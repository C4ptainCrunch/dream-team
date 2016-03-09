package parser;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import models.*;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.functors.Map3;
import org.codehaus.jparsec.functors.Map4;
import org.codehaus.jparsec.pattern.CharPredicates;
import org.codehaus.jparsec.pattern.Patterns;

public class NodeParser {

    public static Parser<String> reference() {
		return Parsers.between(Scanners.string("("), Terminals.Identifier.TOKENIZER.source(), Scanners.string(")"));
	}

	public static Parser<String> label() {
        final Parser<String> source = Patterns.many(CharPredicates.notChar('}')).toScanner("label string").source();
        return Parsers.between(Scanners.isChar('{'),
                source, Scanners.isChar('}'));
	}

	public static Parser<String> anOption() {
		final Parser<String> argument = (Scanners.IDENTIFIER.next(MAYBEWHITESPACES)).many1().source();
		final Parser<Void> decimalWithUnit = Scanners.DECIMAL.next(Scanners.IDENTIFIER.optional()).cast();
		final Parser<String> withEqual = Scanners.isChar('=').next(Parsers.or(Scanners.IDENTIFIER, decimalWithUnit))
				.source();
		final Parser<String> arrows = Scanners.among("><-").many1().source();
		return Parsers.or(argument.next(withEqual.optional()).source(), arrows);
	}

	public static Parser<List<String>> options() {
		final Parser<Void> optionsDelimiter = Scanners.isChar(',').next(MAYBEWHITESPACES);
		return Parsers.between(Scanners.isChar('['), anOption().sepBy(optionsDelimiter), Scanners.isChar(']'));
	}

	public static Parser<Point> coordinates() {
        final Parser<Point> coords = Terminals.DecimalLiteral.TOKENIZER.next(Scanners.string(",")).next(MAYBEWHITESPACES)
                .next(Terminals.DecimalLiteral.TOKENIZER).source().map(s -> {
                        String[] splitted = s.split(",\\s*");
                        return new Point(Math.round(Float.valueOf(splitted[0])),
                                Math.round(Float.valueOf(splitted[1])));
                    }
                );
        return Parsers.between(Scanners.string("("), coords, Scanners.string(")"));
	}

	public static Parser<DestructuredNode> nodeFromDraw() {
		return Parsers.sequence(coordinates(),
				Parsers.sequence(Scanners.WHITESPACES, Scanners.string("node"),
						Parsers.sequence(MAYBEWHITESPACES,
								Parsers.or(options(), Parsers.constant(new ArrayList<String>())))),
				Parsers.sequence(MAYBEWHITESPACES, label()), DestructuredNode::new);
	}

	public static Parser<Void> nodeFromNode(TikzGraph graph) {

        return Parsers.sequence(
				Parsers.sequence(Scanners.string("\\node"), MAYBEWHITESPACES, maybeOptions),
				Parsers.sequence(MAYBEWHITESPACES, maybeReference),
				Parsers.sequence(Scanners.WHITESPACES, Scanners.string("at"), Scanners.WHITESPACES, coordinates()),
				Parsers.sequence(MAYBEWHITESPACES, maybeLabel),
                (options, ref, coord, label) -> {
                    graph.add(createNode(new DestructuredNode(coord, options, label)));
                    return null;
                });
	}

	public static Parser<Void> nodesFromDraw(TikzGraph graph) {
		return Parsers.sequence(
				Parsers.sequence(Scanners.string("\\draw"),
						Parsers.sequence(MAYBEWHITESPACES, maybeOptions)),
				Parsers.sequence(Scanners.WHITESPACES, nodeFromDraw()),
				Parsers.sequence(Scanners.WHITESPACES, Scanners.string("--"), Scanners.WHITESPACES, nodeFromDraw())
						.many(),
                (defaultOptions, firstNode, restNode) -> {
                    TikzNode previous, current;
                    previous = createNode(defaultOptions, firstNode);
                    graph.add(previous);
                    for (DestructuredNode destructuredNode : restNode) {
                        current = createNode(defaultOptions, destructuredNode);
                        graph.add(current);
                        graph.add(previous, new TikzUndirectedEdge(previous,
                                current)); /* TODO: parsing edges */
                        previous = current;
                    }
                    return null;
					});
	}

	private static final Parser<Void> MAYBEWHITESPACES = Scanners.WHITESPACES.optional();
    private static final Parser<List<String>> maybeOptions =  Parsers.or(options(), Parsers.constant(new ArrayList<String>()));
    private static final Parser<String> maybeReference = Parsers.or(reference(), Parsers.constant(""));
    private static final Parser<String> maybeLabel = Parsers.or(label(), Parsers.constant(""));

	public static Parser<Void> parseDocument(TikzGraph graph) {
		return Parsers.between(
				Scanners.string("\\documentclass{article}\n" + "\\usepackage{tikz}\n" + "\\begin{document}\n"
						+ "\\begin{tikzpicture}\n"),
				Parsers.or(nodesFromDraw(graph), edgesFromDraw(graph), nodeFromNode(graph), Scanners.isChar(';')).many()
						.cast(),
				Scanners.string("\\end{tikzpicture}\n" + "\\end{document}"));

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
		for (String s : shapes)
			if (list2.contains(s))
				return s;
		for (String s : shapes)
			if (list1.contains(s))
				return s;
		return "void";
	}

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
                        graph.add(previous, edge);
                        previous = current;
                    }
                    return null;
				});

	}

    private static TikzNode createNode(DestructuredNode node){
        return createNode(new ArrayList<>(), node);
    }

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
		} else
			res = new TikzVoid();
		res.setPosition(node.getCoordinates());
		res.setLabel(node.getLabel());
		return res;
	}

	private static String isDirected(List<String> options) {
		if (options.contains("->")) {
			return "directRight";
		} else if (options.contains("<-")) {
			return "directedLeft";
		} else {
			return ("undirected");
		}
	}

	private static final String[] shapes = new String[] { "rectangle", "circle", "ellipse", "circle split",
			"forbidden sign", "diamond", "cross out", "strike out", "regular polygon", "ann", "star" };
}

class DestructuredNode {
	private Point coordinates;
	private List<String> options;
	private String label;

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
				+ label.toString();
	}
}
