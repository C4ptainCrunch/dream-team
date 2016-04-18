package parser;

import java.awt.*;
import java.util.*;
import java.util.List;

import models.tikz.*;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.functors.*;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.pattern.CharPredicates;
import org.codehaus.jparsec.pattern.Patterns;

public class NodeParser {
    private static final Parser<Void> MAYBEWHITESPACES = Scanners.WHITESPACES.optional();
    private static final Parser<Void> MAYBENEWLINES = Scanners.isChar('\n').optional();
    private static final Parser<List<String>> maybeOptions = Parsers.or(options(),
            Parsers.constant(new ArrayList<String>()));
    private static final Parser<String> maybeLabel = Parsers.or(label(), Parsers.constant(""));


    private NodeParser() {
    }

    public static Parser<String> reference() {
        return Parsers.between(Scanners.string("("),
                Parsers.or(Terminals.Identifier.TOKENIZER.source(), Parsers.constant("")), Scanners.string(")"));
    }

    public static Parser<Integer> decimal() {
        return Parsers.sequence(Scanners.string("-").optional().source(), MAYBEWHITESPACES.next(Terminals.DecimalLiteral.TOKENIZER).source(),
                (minus, nums) -> Math.round(Float.parseFloat(minus + nums)));
    }

    public static Parser<String> label() {
        final Parser<String> source = Patterns.many(CharPredicates.notChar('}')).toScanner("label string").source();
        return Parsers.between(Scanners.isChar('{'), source, Scanners.isChar('}'));
    }

    public static Parser<String> anOption() {
        final Parser<String> argument = Scanners.IDENTIFIER.next(MAYBEWHITESPACES).many1().source();
        final Parser<Void> decimalWithUnit = Scanners.DECIMAL.next(Scanners.IDENTIFIER.optional()).cast();
        final Parser<String> withEqual = Scanners.isChar('=').next(MAYBEWHITESPACES).next(Parsers.or(Scanners.IDENTIFIER, decimalWithUnit))
                .source();
        final Parser<String> arrows = Scanners.among("><-").many1().source();
        return Parsers.or(argument.next(withEqual.optional()).source(), arrows);
    }

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

    public static Parser<List<String>> options() {
        final Parser<Void> optionsDelimiter = Scanners.isChar(',').next(MAYBEWHITESPACES);
        return Parsers.between(Scanners.isChar('['), anOption().sepBy(optionsDelimiter), Scanners.isChar(']'));
    }

    public static Parser<Point> coordinates() {
        final Parser<Void> sep = Parsers.sequence(MAYBEWHITESPACES, Scanners.isChar(','), MAYBEWHITESPACES);
        final Parser<Point> coord = Parsers.sequence(decimal(), sep.next(decimal()), Point::new);
        return Parsers.between(Scanners.isChar('('), coord, Scanners.isChar(')'));
    }

    public static Parser<DestructuredNode> nodeFromDraw() {
        return Parsers.sequence(coordinates(),
                Parsers.sequence(Scanners.WHITESPACES, Scanners.string("node"),
                        Parsers.sequence(MAYBEWHITESPACES,
                                Parsers.or(options(), Parsers.constant(new ArrayList<>())))),
                Parsers.sequence(MAYBEWHITESPACES, label()), DestructuredNode::new);
    }

    public static Parser<Void> nodeFromNode(TikzGraph graph) {

        return Parsers.sequence(Parsers.sequence(Scanners.string("\\node"), MAYBEWHITESPACES, maybeOptions),
                Parsers.sequence(MAYBEWHITESPACES, reference()),
                Parsers.sequence(Scanners.WHITESPACES, Scanners.string("at"), Scanners.WHITESPACES, coordinates()),
                Parsers.sequence(MAYBEWHITESPACES, maybeLabel), (options, ref, coord, label) -> {
                    graph.add(createNode(new DestructuredNode(coord, options, label)));
                    return null;
                });
    }

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

    public static Parser<Void> parseDocument(TikzGraph graph) {
        return Parsers.or(nodesFromDraw(graph), edgesFromDraw(graph), nodeFromNode(graph), Scanners.isChar(';'),
                MAYBEWHITESPACES).many().cast();

    }

    public static Parser<Void> parseTeXDocument(TikzGraph graph) {
        return Parsers.between(parseTexPrelude(), parseDocument(graph), parseTexPostlude());

    }

    public static Parser<Void> parseTexPrelude() {
        return Parsers.sequence(Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many(),
                Scanners.string("\\documentclass{article}\n"), Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many(),
                Scanners.string("\\usepackage{tikz}\n"), Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many(),
                Scanners.string("\\begin{document}\n"), Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many(),
                Scanners.string("\\begin{tikzpicture}\n"), Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many()).cast();
    }

    public static Parser<Void> parseTexPostlude() {
        return Parsers.sequence(Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many(),
                Scanners.string("\\end{tikzpicture}\n"), Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many(),
                Scanners.string("\\end{document}"), Parsers.or(MAYBEWHITESPACES, MAYBENEWLINES).many()).cast();
    }
}



/*class DestructuredNode {
    private final Point coordinates;
    private final List<String> options;
    private final String label;

    public DestructuredNode(Point s, List<String> t, String u) {
        coordinates = s;
        options = t;
        label = u;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Coordinates: " + coordinates.toString() + ", Options: " + options.toString() + ", Label: "
                + label;
    }
}*/