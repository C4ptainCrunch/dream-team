package parser;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import models.tikz.*;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.pattern.CharPredicates;
import org.codehaus.jparsec.pattern.Patterns;

/**
 * This class parses the tikz code written in the Editor
 * and constructs the corresponding tikz objects into
 * the current project's tikz graph.
 * This class uses the diff-match-patch library
 */
public class NodeParser {
    private static final Parser<Void> MAYBEWHITESPACES = Scanners.WHITESPACES.optional();
    private static final Parser<Void> MAYBENEWLINES = Scanners.isChar('\n').optional();
    private static final Parser<List<String>> maybeOptions = Parsers.or(options(),
            Parsers.constant(new ArrayList<String>()));
    private static final Parser<String> maybeLabel = Parsers.or(label(), Parsers.constant(""));
    private static final String[] shapes = new String[] { "rectangle", "circle", "ellipse", "circle split",
            "forbidden sign", "diamond", "cross out", "strike out", "regular polygon", "ann", "star" };

    /**
     * Constructs an empty NodeParser
     */
    private NodeParser() {
    }

    /**
     * Parses a tikz reference.
     * A reference is tikz code written between "(" and ")".
     * In order to get the string situated between the parentheses,
     * one should call parse() on the returned parser.
     * @return a parser object that contains the string between the parentheses
     */
    public static Parser<String> reference() {
        return Parsers.between(Scanners.string("("),
                Parsers.or(Terminals.Identifier.TOKENIZER.source(), Parsers.constant("")), Scanners.string(")"));
    }

    /**
     * Parses a tikz label.
     * A label is tikz code written between "{" and "}".
     * In order to get the string situated between the braces,
     * one should call parse() on the returned parser.
     * @return a parser object that contains the string between the braces
     */
    public static Parser<String> label() {
        final Parser<String> source = Patterns.many(CharPredicates.notChar('}')).toScanner("label string").source();
        return Parsers.between(Scanners.isChar('{'), source, Scanners.isChar('}'));
    }

    /**
     * Parses an array of tikz options.
     * Options are tikz code written between "[" and "]" and separated by commmas.
     * In order to get the string array containing the options situated between the hooks,
     * one should call parse() on the returned parser.
     * @return a parser object that contains the array of strings between the hooks
     */
    public static Parser<List<String>> options() {
        final Parser<Void> optionsDelimiter = Scanners.isChar(',').next(MAYBEWHITESPACES);
        return Parsers.between(Scanners.isChar('['), anOption().sepBy(optionsDelimiter), Scanners.isChar(']'));
    }

    /**
     * Parses a tikz option.
     * An option is tikz code representing a single option situated in an array of options
     * that is written between "[" and "]" separated by commas.
     * @return a parser object that contains a single option
     */
    public static Parser<String> anOption() {
        final Parser<String> argument = Scanners.IDENTIFIER.next(MAYBEWHITESPACES).many1().source();
        final Parser<Void> decimalWithUnit = Scanners.DECIMAL.next(Scanners.IDENTIFIER.optional()).cast();
        final Parser<String> withEqual = Scanners.isChar('=').next(Parsers.or(Scanners.IDENTIFIER, decimalWithUnit))
                .source();
        final Parser<String> arrows = Scanners.among("><-").many1().source();
        return Parsers.or(argument.next(withEqual.optional()).source(), arrows);
    }

    /**
     * Parses a tikz coordinates.
     * A coordinates is tikz code written between "(" and ")",
     * containing two integer separated by commas
     * In order to get the Point object situated between the parentheses,
     * one should call parse() on the returned parser.
     * @return a parser object that represents the Point object between the parentheses
     */
    public static Parser<Point> coordinates() {
        final Parser<Point> coords = Terminals.DecimalLiteral.TOKENIZER.next(Scanners.string(","))
                .next(MAYBEWHITESPACES).next(Terminals.DecimalLiteral.TOKENIZER).source().map(s -> {
                    String[] splitted = s.split(",\\s*");
                    return new Point(Math.round(Float.valueOf(splitted[0])), Math.round(Float.valueOf(splitted[1])));
                });
        return Parsers.between(Scanners.string("("), coords, Scanners.string(")"));
    }

    /**
     * Parses a single tikz node from a \draw tikz command
     * @return a parser object that represents the single tikz node
     */
    public static Parser<DestructuredNode> nodeFromDraw() {
        return Parsers.sequence(coordinates(),
                Parsers.sequence(Scanners.WHITESPACES, Scanners.string("node"),
                        Parsers.sequence(MAYBEWHITESPACES,
                                Parsers.or(options(), Parsers.constant(new ArrayList<String>())))),
                Parsers.sequence(MAYBEWHITESPACES, label()), DestructuredNode::new);
    }

    /**
     * Parses a single tikz node from a \node tikz command
     * @return a parser object that represents the single tikz node
     */
    public static Parser<Void> nodeFromNode(TikzGraph graph) {

        return Parsers.sequence(Parsers.sequence(Scanners.string("\\node"), MAYBEWHITESPACES, maybeOptions),
                Parsers.sequence(MAYBEWHITESPACES, reference()),
                Parsers.sequence(Scanners.WHITESPACES, Scanners.string("at"), Scanners.WHITESPACES, coordinates()),
                Parsers.sequence(MAYBEWHITESPACES, maybeLabel), (options, ref, coord, label) -> {
                    graph.add(createNode(new DestructuredNode(coord, options, label)));
                    return null;
                });
    }

    /**
     * Parses several nodes from \draw tikz commands
     * and adds them into a given tikz graph
     * @param graph The tikz graph to adds the parsed nodes to
     * @return a void parser object
     */
    public static Parser<Void> nodesFromDraw(TikzGraph graph) {
        return Parsers
                .sequence(Parsers.sequence(Scanners.string("\\draw"), Parsers.sequence(MAYBEWHITESPACES, maybeOptions)),
                        Parsers.sequence(Scanners.WHITESPACES,
                                nodeFromDraw()),
                        Parsers.sequence(Scanners.WHITESPACES, Scanners.string("--"), Scanners.WHITESPACES, nodeFromDraw())
                                .many(), (defaultOptions, firstNode, restNode) -> {
                            TikzNode previous;
                            TikzNode current;
                            previous = createNode(defaultOptions, firstNode);
                            graph.add(previous);
                            for (DestructuredNode destructuredNode : restNode) {
                                current = createNode(defaultOptions, destructuredNode);
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
     * @param graph The tikz graph to adds the parsed nodes/edges to
     * @return a void parser object
     */
    public static Parser<Void> parseDocument(TikzGraph graph) {
        return Parsers.or(nodesFromDraw(graph), edgesFromDraw(graph), nodeFromNode(graph), Scanners.isChar(';'),
                MAYBEWHITESPACES).many().cast();

    }

    /**
     * Parses a tex document containing tikz code
     * with its tex prelude and tex postlude.
     * The nodes and edges created in the tikz code
     * are added to the given tikz graph
     * @param graph The tikz graph to adds the parsed nodes/edges to
     * @return a void parser object
     */
    public static Parser<Void> parseTeXDocument(TikzGraph graph) {
        return Parsers.between(parseTexPrelude(), parseDocument(graph), parseTexPostlude());

    }

    /**
     * Parses the prelude of a tex document
     * containing tikz code
     * @return a void parser object
     */
    public static Parser<Void> parseTexPrelude() {
        return Parsers.sequence(Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many(),
                Scanners.string("\\documentclass{article}\n"), Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many(),
                Scanners.string("\\usepackage{tikz}\n"), Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many(),
                Scanners.string("\\begin{document}\n"), Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many(),
                Scanners.string("\\begin{tikzpicture}\n"), Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many()).cast();
    }

    /**
     * Parses the postlude of a tex document
     * containing tikz code
     * @return a void parser object
     */
    public static Parser<Void> parseTexPostlude() {
        return Parsers.sequence(Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many(),
                Scanners.string("\\end{tikzpicture}\n"), Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many(),
                Scanners.string("\\end{document}"), Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many()).cast();
    }


    private static String getNodeShape(List<String> list) {
        /*
         * Testing for shape by priority in a string like
         * "\draw[circle] (-0.2,0) -- (4.2,0) node[rectangle] {$x$};", rectangle
         * by default
         */
        return getNodeShape(list, new ArrayList<>());
    }

    private static String getNodeShape(List<String> list1, List<String> list2) {
        /*
         * Testing for shape by priority in a string like
         * "\draw[circle] (-0.2,0) -- (4.2,0) node[rectangle] {$x$};", rectangle
         * by default
         */
        for (String s : shapes) {
            if (list2.contains(s)) {
                return s;
            }
        }
        for (String s : shapes) {
            if (list1.contains(s)) {
                return s;
            }
        }
        return "void";
    }

    /**
     * Parses several edges from \draw tikz commands
     * and adds them into a given tikz graph
     * @param graph The tikz graph to adds the parsed edges to
     * @return a void parser object
     */
    public static Parser<Void> edgesFromDraw(TikzGraph graph) {
        return Parsers.sequence(
                Parsers.sequence(Scanners.string("\\draw"),
                        Parsers.or(options(), Parsers.constant(new ArrayList<String>()))),
                Parsers.sequence(Scanners.WHITESPACES, coordinates()),
                Parsers.sequence(Scanners.WHITESPACES, Scanners.string("--"), Scanners.WHITESPACES, coordinates())
                        .many(),
                (defaultOptions, firstCoord, restCoord) -> {
                    TikzVoid previous = new TikzVoid();
                    TikzVoid current;
                    TikzEdge edge;
                    graph.add(previous);
                    for (Point coord : restCoord) {
                        current = new TikzVoid();
                        graph.add(current);
                        switch (isDirected(defaultOptions)) {
                            case "directedRight":
                                edge = new TikzDirectedEdge(previous, current);
                                break;
                            case "directedLeft":
                                edge = new TikzDirectedEdge(current, previous);
                                break;
                            default:
                                edge = new TikzUndirectedEdge(previous, current);
                                break;
                        }
                        graph.add(current);
                        graph.add(edge);
                        previous = current;
                    }
                    return null;
                });

    }

    /**
     * Creates a tikz node from a destructured node
     * @param node The destructured node
     * @return the tikz node created from the destructured node
     */
    private static TikzNode createNode(DestructuredNode node) {
        return createNode(new ArrayList<>(), node);
    }

    /**
     * Creates a tikz node from a destructured node
     * and a list of options defining this node
     * @param defaultOptions The options defining the node
     * @param node The destructured node
     * @return The tikz node created from the destructured node and the option list
     */
    private static TikzNode createNode(List<String> defaultOptions, DestructuredNode node) {
        final String shape = getNodeShape(defaultOptions, node.getOptions());
        TikzNode res;
        final HashSet<String> rectangles = new HashSet<>(Arrays.asList("rectangle", "diamond"));
        final HashSet<String> circles = new HashSet<>(
                Arrays.asList("circle", "ellipse", "circle split", "forbidden sign"));
        final HashSet<String> polygons = new HashSet<>(Arrays.asList("regular polygon", "star"));
        if (rectangles.contains(shape)) {
            res = new TikzRectangle();
        } else if (circles.contains(shape)) {
            res = new TikzCircle();
        } else if (polygons.contains(shape)) {
            res = new TikzPolygon();
        } else {
            res = new TikzVoid();
        }
        res.setPosition(node.getCoordinates());
        res.setLabel(node.getLabel());
        return res;
    }

    /**
     * Returns a string defining the type of the edges
     * linking the nodes in a tikz command.
     * @param options The options list of the tikz command
     * @return whether the edges are left/right directed or undirected
     */
    private static String isDirected(List<String> options) {
        if (options.contains("->")) {
            return "directRight";
        } else if (options.contains("<-")) {
            return "directedLeft";
        } else {
            return "undirected";
        }
    }
}

/**
 * This class contains the elements representing
 * a tikz node and is configured while parsing the tikz text
 */
class DestructuredNode {
    private final Point coordinates;
    private final List<String> options;
    private final String label;

    /**
     * Constructs a new Destructured node with
     * given coordinates, options and label
     * @param s The coordinates
     * @param t The options
     * @param u The label
     */
    public DestructuredNode(Point s, List<String> t, String u) {
        coordinates = s;
        options = t;
        label = u;
    }

    /**
     * Getter for the coordinates of the destructured node
     * @return the Point object representing the coordinates
     */
    public Point getCoordinates() {
        return coordinates;
    }

    /**
     * Getter for the options defining the destructured node
     * @return the array of options
     */
    public List<String> getOptions() {
        return options;
    }

    /**
     * Getter for the label of the destructured node
     * @return The label of the destructured node
     */
    public String getLabel() {
        return label;
    }

    /**
     * Transforms this destructured node into a string representation
     * @return The string representation
     */
    @Override
    public String toString() {
        return "Coordinates: " + coordinates.toString() + ", Options: " + options.toString() + ", Label: "
                + label;
    }
}
