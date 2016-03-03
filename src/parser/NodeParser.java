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
import org.codehaus.jparsec.pattern.Patterns;

public class NodeParser {
	public static Parser<String> reference() {
		return Parsers.between(Scanners.string("("), Terminals.Identifier.TOKENIZER.source(), Scanners.string(")"));
	}

	public static Parser<String> label() {
		return Patterns.regex("[^}]").many().toScanner("name string")
				.between(Scanners.isChar('{'), Scanners.isChar('}')).source().map(s -> s.substring(1, s.length() - 1));
	}

	public static Parser<List<String>> options() {
		return Patterns.regex("[^]]").many().toScanner("options string")
				.between(Scanners.isChar('['), Scanners.isChar(']')).source()
				.map(s -> Arrays.asList(s.substring(1, s.length() - 1).split(", *")));
	}

	public static Parser<Point> coordinates() {
		return Parsers.between(Scanners.string("("), Terminals.DecimalLiteral.TOKENIZER.next(Scanners.string(","))
				.next(Terminals.DecimalLiteral.TOKENIZER).source().map(new Map<String, Point>() {
					@Override
					public Point map(String s) {
						String[] splitted = s.split(",");
						return new Point(Math.round(Float.valueOf(splitted[0])),
								Math.round(Float.valueOf(splitted[1])));
					}
				}), Scanners.string(")"));
	}

	public static Parser<TikzNode> nodeFromDraw() {
		return Parsers.sequence(coordinates(),
				Parsers.sequence(Scanners.WHITESPACES, Scanners.string("node"),
						Parsers.or(options(), Parsers.constant(new ArrayList<String>()))),
				Parsers.sequence(Scanners.WHITESPACES, label()), new Map3<Point, List<String>, String, TikzNode>() {
					@Override
					public TikzNode map(Point point, List<String> strings, String s) {
						if (strings.contains("circle"))
							return new TikzCircle();
						if (strings.contains("triangle"))
							return new TikzTriangle();
						return new TikzRectangle();
					}
				});
	}

	public static Parser<TikzNode> nodeFromNode() {
		return Parsers.sequence(
				Parsers.sequence(Scanners.string("\\node"),
						Parsers.or(options(), Parsers.constant(new ArrayList<String>()))),
				Parsers.or(reference(), Parsers.constant("")),
				Parsers.sequence(Scanners.WHITESPACES, Scanners.string("at"), Scanners.WHITESPACES, coordinates()),
				Parsers.or(label(), Parsers.constant("")), new Map4<List<String>, String, Point, String, TikzNode>() {
					@Override
					public TikzNode map(List<String> strings, String s, Point point, String s2) {
						if (strings.contains("circle"))
							return new TikzCircle();
						if (strings.contains("triangle"))
							return new TikzTriangle();
						return new TikzRectangle();
					}
				});
	}

	public static Parser<Void> nodesFromDraw(TikzGraph graph) {
		return Parsers.sequence(
				Parsers.sequence(Scanners.string("\\draw"),
						Parsers.or(options(), Parsers.constant(new ArrayList<String>()))),
				Parsers.sequence(Scanners.WHITESPACES, nodeFromDraw()),
				Parsers.sequence(Scanners.WHITESPACES, Scanners.string("--"), Scanners.WHITESPACES, nodeFromDraw())
						.many(),
				new Map3<List<String>, TikzNode, List<TikzNode>, Void>() {
					@Override
					public Void map(List<String> options, TikzNode tikzNode, List<TikzNode> tikzNodes) {
						String type = options.contains("triangle") ? "triangle"
								: options.contains("circle") ? "circle" : "rectangle";
						// missing downcast constructors
						graph.add(tikzNode);
						TikzNode previous = tikzNode;
						for (TikzNode node : tikzNodes) {
							graph.add(node);
							graph.add(previous, new TikzUndirectedEdge(previous, node));
							previous = node;
						}
						return null;
					}
				});
	}
}
