package parser;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
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
		return Parsers.between(Scanners.isChar('{'),
				Patterns.many(CharPredicates.notChar('}')).toScanner("label string").source(), Scanners.isChar('}'));
	}

	public static Parser<List<String>> options() {
        return Patterns.regex("[^]]").many().toScanner("options string")
				.between(Scanners.isChar('['), Scanners.isChar(']')).source()
				.map(s -> Arrays.asList(s.substring(1, s.length() - 1).split(",\\s*")));
	}

	public static Parser<Point> coordinates() {
		return Parsers.between(Scanners.string("("), Terminals.DecimalLiteral.TOKENIZER.next(Scanners.string(","))
				.next(Terminals.DecimalLiteral.TOKENIZER).source().map(new Map<String, Point>() {
					@Override
					public Point map(String s) {
						String[] splitted = s.split(",\\s*");
						return new Point(Math.round(Float.valueOf(splitted[0])),
								Math.round(Float.valueOf(splitted[1])));
					}
				}), Scanners.string(")"));
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
				Parsers.sequence(Scanners.string("\\node"),
						Parsers.sequence(MAYBEWHITESPACES,
								Parsers.or(options(), Parsers.constant(new ArrayList<String>())))),
				Parsers.sequence(MAYBEWHITESPACES, Parsers.or(reference(), Parsers.constant(""))),
				Parsers.sequence(Scanners.WHITESPACES, Scanners.string("at"), Scanners.WHITESPACES, coordinates()),
				Parsers.sequence(MAYBEWHITESPACES, Parsers.or(label(), Parsers.constant(""))),
				new Map4<List<String>, String, Point, String, Void>() {
					@Override
					public Void map(List<String> options, String ref, Point coord, String label) {
						TikzNode result;
						switch (NodeParser.getNodeShape(options)) {
						case "circle":
							graph.add(new TikzCircle());
							break;
						case "triangle":
							graph.add(new TikzTriangle());
							break;
						default:
							graph.add(new TikzRectangle());
							break;
						}
						return null;
					}
				});
	}

	public static Parser<Void> nodesFromDraw(TikzGraph graph) {
		/* Attention: missing specific constructors */
		return Parsers.sequence(
				Parsers.sequence(Scanners.string("\\draw"),
						Parsers.sequence(MAYBEWHITESPACES,
								Parsers.or(options(), Parsers.constant(new ArrayList<String>())))),
				Parsers.sequence(Scanners.WHITESPACES, nodeFromDraw()),
				Parsers.sequence(Scanners.WHITESPACES, Scanners.string("--"), Scanners.WHITESPACES, nodeFromDraw())
						.many(),
				new Map3<List<String>, DestructuredNode, List<DestructuredNode>, Void>() {
					@Override
					public Void map(List<String> defaultOptions, DestructuredNode firstNode,
							List<DestructuredNode> restNode) {
						TikzNode previous, current;
						switch (NodeParser.getNodeShape(defaultOptions, firstNode.getOptions())) {
						case "circle":
							previous = new TikzCircle();
							break;
						case "triangle":
							previous = new TikzTriangle();
							break;
						default:
							previous = new TikzRectangle();
						}
						graph.add(previous);
						for (DestructuredNode destructuredNode : restNode) {
							switch (NodeParser.getNodeShape(defaultOptions, destructuredNode.getOptions())) {
							case "circle":
								current = new TikzCircle();
								break;
							case "triangle":
								current = new TikzTriangle();
								break;
							default:
								current = new TikzRectangle();
							}
							graph.add(current);
							graph.add(previous, new TikzUndirectedEdge(previous,
									current)); /* TODO: parsing edges */
							previous = current;
						}
						return null;
					}
				});
	}

	private static final Parser<Void> MAYBEWHITESPACES = Scanners.WHITESPACES.optional();

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
		String[] shapes = new String[] { "circle", "triangle", "rectangle" };
		for (String s : shapes)
			if (list.contains(s))
				return s;
		return "rectangle";
	}

	private static String getNodeShape(List<String> list1, List<String> list2) {
		/*
		 * Testing for shape by priority in a string like
		 * "\draw[circle] (-0.2,0) -- (4.2,0) node[rectangle] {$x$};", rectangle
		 * by default
		 */
		String[] shapes = new String[] { "circle", "triangle", "rectangle" };
		for (String s : shapes)
			if (list2.contains(s))
				return s;
		for (String s : shapes)
			if (list1.contains(s))
				return s;
		return "rectangle";
	}

	public static Parser<Void> edgesFromDraw(TikzGraph graph) {
		return Parsers.sequence(
				Parsers.sequence(Scanners.string("\\draw"),
						Parsers.or(options(), Parsers.constant(new ArrayList<String>()))),
				Parsers.sequence(Scanners.WHITESPACES, coordinates()),
				Parsers.sequence(Scanners.WHITESPACES, Scanners.string("--"), Scanners.WHITESPACES, coordinates())
						.many(),
				new Map3<List<String>, Point, List<Point>, Void>() {
					@Override
					public Void map(List<String> defaultOptions, Point firstCoord, List<Point> restCoord) {
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
					}
				});

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
