package parser;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

import models.tikz.*;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.pattern.CharPredicates;
import org.codehaus.jparsec.pattern.Patterns;

/**
 * This class parses the tikz code written in the Editor and constructs the
 * corresponding tikz objects into the current project's tikz graph. This class
 * uses the diff-match-patch library
 */
public class NodeParser {
    private static final Parser<Void> MAYBEWHITESPACES = Scanners.WHITESPACES.optional();
    private static final Parser<Void> MAYBENEWLINES = Scanners.isChar('\n').optional();
    private static final Parser<HashMap<String, String>> maybeOptions = Parsers.or(optionsParser(), Parsers.constant(new HashMap<>()));
    private static final Parser<String> maybeLabel = Parsers.or(label(), Parsers.constant(""));

    /**
     * Constructs an empty NodeParser
     */
    private NodeParser() {
    }

    /**
     * Parses a tikz reference. A reference is tikz code written between "(" and
     * ")". In order to get the string situated between the parentheses, one
     * should call parse() on the returned parser.
     *
     * @return a parser object that contains the string between the parentheses
     */
    public static Parser<String> reference() {
        return Parsers.between(Scanners.string("("), Scanners.notChar(')').many().source(),
                Scanners.string(")"));
    }

    public static Parser<Integer> decimal() {
        return Parsers.sequence(Scanners.string("-").optional().source(),
                MAYBEWHITESPACES.next(Terminals.DecimalLiteral.TOKENIZER).source(),
                (minus, nums) -> Math.round(Float.parseFloat(minus + nums)));
    }

    /**
     * Parses a tikz label. A label is tikz code written between "{" and "}". In
     * order to get the string situated between the braces, one should call
     * parse() on the returned parser.
     *
     * @return a parser object that contains the string between the braces
     */
    public static Parser<String> label() {
        final Parser<String> source = Patterns.many(CharPredicates.notChar('}')).toScanner("label string").source();
        return Parsers.between(Scanners.isChar('{'), source, Scanners.isChar('}'));
    }

    /**
     * Parses a tikz option. An option is tikz code representing a single option
     * situated in an array of options that is written between "[" and "]"
     * separated by commas.
     *
     * @return a parser object that contains a single option
     */
    public static Parser<String> anOption() {
        final Parser<String> argument = Scanners.IDENTIFIER.next(MAYBEWHITESPACES).many1().source();
        final Parser<Void> decimalWithUnit = Scanners.DECIMAL.next(Scanners.IDENTIFIER.optional()).cast();
        final Parser<String> withEqual = Scanners.isChar('=').next(MAYBEWHITESPACES).next(Parsers.or(Scanners.IDENTIFIER, decimalWithUnit))
                .source();
        final Parser<String> arrows = Scanners.among("><-").many1().source();
        return Parsers.or(argument.next(withEqual.optional()).source(), arrows);
    }

    /**
     * Parses an array of tikz options. Options are tikz code written between
     * "[" and "]" and separated by commmas. In order to get the string array
     * containing the options situated between the hooks, one should call
     * parse() on the returned parser.
     *
     * @return a parser object that contains a map of options with their value
     *         or an empty string
     */
    public static Parser<HashMap<String, String>> optionsParser() {
        final Parser<Void> sep = Scanners.isChar(',').next(MAYBEWHITESPACES);
        final Parser<List<String>> options = Parsers.between(Scanners.isChar('['), anOption().sepBy(sep), Scanners.isChar(']'));
        return options.map(strings -> {
            HashMap<String, String> res = new HashMap<>();
            strings.stream().forEach(s -> {
                String[] split = s.split("\\s*=\\s*");
                res.put(split[0], split.length == 1 ? "" : split[1]);
            });
            return res;
        });
    }

    /**
     * Parses a tikz coordinates. A coordinates is tikz code written between "("
     * and ")", containing two integer separated by commas In order to get the
     * Point object situated between the parentheses, one should call parse() on
     * the returned parser.
     *
     * @return a parser object that represents the Point object between the
     *         parentheses
     */
    public static Parser<Point> coordinates() {
        final Parser<Void> sep = Parsers.sequence(MAYBEWHITESPACES, Scanners.isChar(','), MAYBEWHITESPACES);
        final Parser<Point> coord = Parsers.sequence(decimal(), sep.next(decimal()), Point::new);
        return Parsers.between(Scanners.isChar('('), coord, Scanners.isChar(')'));
    }

    /**
     * Parses a single tikz node from a \draw tikz command
     *
     * @return a parser object that represents the single tikz node
     */
    public static Parser<DestructuredNode> nodeFromDraw() {
        return Parsers.sequence(coordinates(),
                Parsers.sequence(Scanners.WHITESPACES, Scanners.string("node"), MAYBEWHITESPACES, maybeOptions),
                Parsers.sequence(MAYBEWHITESPACES, label()), DestructuredNode::new);
    }

    /**
     * Parses a single tikz node from a \node tikz command
     *
     * @return a parser object that represents the single tikz node
     */
    public static Parser<Void> nodeFromNode(TikzGraph graph) {
        return Parsers.sequence(Parsers.sequence(Scanners.string("\\node"), MAYBEWHITESPACES, maybeOptions),
                Parsers.sequence(MAYBEWHITESPACES, reference()),
                Parsers.sequence(Scanners.WHITESPACES, Scanners.string("at"), Scanners.WHITESPACES, coordinates()),
                Parsers.sequence(MAYBEWHITESPACES, maybeLabel), (options, ref, coord, label) -> {
                    graph.add(Utils.createNode(new DestructuredNode(options, ref, coord, label)));
                    return null;
                });
    }

    /**
     * Parses several nodes from \draw tikz commands and adds them into a given
     * tikz graph
     *
     * @param graph
     *            The tikz graph to adds the parsed nodes to
     * @return a void parser object
     */
    public static Parser<Void> nodesFromDraw(TikzGraph graph) {
        return Parsers.sequence(Parsers.sequence(Scanners.string("\\draw"), Parsers.sequence(MAYBEWHITESPACES, maybeOptions)),
                Parsers.sequence(Scanners.WHITESPACES, nodeFromDraw()),
                Parsers.sequence(Scanners.WHITESPACES, Scanners.string("--"), Scanners.WHITESPACES, nodeFromDraw()).many(),
                (defaultOptions, firstNode, restNode) -> {
                    TikzNode previous;
                    TikzNode current;
                    previous = Utils.createNode(defaultOptions, firstNode);
                    graph.add(previous);
                    for (DestructuredNode destructuredNode : restNode) {
                        current = Utils.createNode(defaultOptions, destructuredNode);
                        graph.add(current);
                        graph.add(new TikzUndirectedEdge(previous,
                                current)); /* TODO: parsing edges */
                        previous = current;
                    }
                    return null;
                });
    }

    /**
     * Parses a tikz document that may contains several commands such as
     * creating nodes/edges from \draw commands and nodes from \node commands
     * and adds them into a given tikz graph
     *
     * @param graph
     *            The tikz graph to adds the parsed nodes/edges to
     * @return a void parser object
     */
    public static Parser<Void> parseDocument(TikzGraph graph) {
        return Parsers.or(nodesFromDraw(graph), edgesFromDraw(graph), nodeFromNode(graph), Scanners.isChar(';'), MAYBEWHITESPACES).many()
                .cast();

    }

    /**
     * Parses a tex document containing tikz code with its tex prelude and tex
     * postlude. The nodes and edges created in the tikz code are added to the
     * given tikz graph
     *
     * @param graph
     *            The tikz graph to adds the parsed nodes/edges to
     * @return a void parser object
     */
    public static Parser<Void> parseTeXDocument(TikzGraph graph) {
        return Parsers.between(parseTexPrelude(), parseDocument(graph), parseTexPostlude());

    }

    /**
     * Parses the prelude of a tex document containing tikz code
     *
     * @return a void parser object
     */
    public static Parser<Void> parseTexPrelude() {
        return Parsers.sequence(Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many(), Scanners.string("\\documentclass{article}\n"),
                Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES), Scanners.string("\\usepackage{tikz}\n"),
                Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES), Scanners.string("\\begin{document}\n"),
                Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES), Scanners.string("\\begin{tikzpicture}\n"),
                Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES)).cast();
    }

    /**
     * Parses the postlude of a tex document containing tikz code
     *
     * @return a void parser object
     */
    public static Parser<Void> parseTexPostlude() {
        return Parsers.sequence(Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES), Scanners.string("\\end{tikzpicture}\n"),
                Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES), Scanners.string("\\end{document}"),
                Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES)).cast();
    }

    /**
     * parses edges from format like \draw[options] (n, m) -- (o, p) -- ...
     *
     * @param graph
     *            The tikz graph to adds the parsed nodes/edges to
     * @return a void parser object
     */
    public static Parser<Void> edgesFromDraw(TikzGraph graph) {
        return Parsers.sequence(Parsers.sequence(Scanners.string("\\draw"), maybeOptions),
                Parsers.sequence(Scanners.WHITESPACES, coordinates()),
                Parsers.sequence(Scanners.WHITESPACES, Scanners.string("--"), Scanners.WHITESPACES, coordinates()).many(),
                (defaultOptions, firstCoord, restCoord) -> {
                    TikzVoid previous = new TikzVoid();
                    TikzVoid current;
                    TikzEdge edge;
                    graph.add(previous);
                    for (Point coord : restCoord) {
                        current = new TikzVoid();
                        graph.add(current);
                        edge = Utils.createEdge(defaultOptions, previous, current);
                        graph.add(current);
                        graph.add(edge);
                        previous = current;
                    }
                    return null;
                });

    }
}
