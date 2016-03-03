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

	public static Parser<Triple<Point, List<String>, String>> nodeFromDraw() {
		return Parsers.sequence(coordinates(),
				Parsers.sequence(Scanners.WHITESPACES, Scanners.string("node"),
						Parsers.or(options(), Parsers.constant(new ArrayList<String>()))),
				Parsers.sequence(Scanners.WHITESPACES, label()), (coord, options, label) -> new Triple<>(coord, options, label));
	}

	public static Parser<TikzNode> nodeFromNode() {
		return Parsers.sequence(
				Parsers.sequence(Scanners.string("\\node"),
						Parsers.or(options(), Parsers.constant(new ArrayList<String>()))),
				Parsers.or(reference(), Parsers.constant("")),
				Parsers.sequence(Scanners.WHITESPACES, Scanners.string("at"), Scanners.WHITESPACES, coordinates()),
				Parsers.or(label(), Parsers.constant("")), new Map4<List<String>, String, Point, String, TikzNode>() {
					@Override
					public TikzNode map(List<String> options, String ref, Point coord, String label) {
						switch (NodeParser.getNodeShape(options)) {
                            case "circle": return new TikzCircle();
                            case "triangle": return new TikzTriangle();
                            default: return new TikzRectangle();
                        }
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
                new Map3<List<String>, Triple<Point, List<String>, String>, List<Triple<Point, List<String>, String>>, Void>() {
                    @Override
                    public Void map(List<String> defaultOptions, Triple<Point, List<String>, String> firstNode, List<Triple<Point, List<String>, String>> restNode) {
                        TikzNode previous, current;
                        switch (NodeParser.getNodeShape(defaultOptions, firstNode.getOptions())) {
                            case "circle": previous = new TikzCircle(); break;
                            case "triangle": previous = new TikzTriangle(); break;                              /* Attention: missing specific constructors */
                            default: previous = new TikzRectangle();
                        }
                        graph.add(previous);
                        for (Triple<Point, List<String>, String> triple: restNode) {
                            switch (NodeParser.getNodeShape(defaultOptions, triple.getOptions())) {
                                case "circle": current = new TikzCircle(); break;
                                case "triangle": current = new TikzTriangle(); break;
                                default: current = new TikzRectangle();
                            }
                            graph.add(current);
                            graph.add(previous, new TikzUndirectedEdge(previous, current));             /* TODO: parsing edges */
                            previous = current;
                        }
                        return null;
                    }
                });
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
                        for(Point coord : restCoord){
                            current = new TikzVoid();
                            graph.add(current);
                            switch(isDirected(defaultOptions)){
                                case "directedRight" : edge = new TikzDirectedEdge(previous,current); break;
                                case "directedLeft" : edge = new TikzDirectedEdge(current,previous); break;
                                default : edge = new TikzUndirectedEdge(previous, current); break;
                            }
                            graph.add(current);
                            graph.add(previous, edge);
                            previous = current;
                        }
                        return null;
                    }
                });

    }

    private static String isDirected(List<String> options){
        if(options.contains("->")){
            return "directRight";
        }
        else if(options.contains("<-")){
            return "directedLeft";
        }
        else{return("undirected");}
    }


    private static String getNodeShape(List<String> list) {
        /* Testing for shape by priority in a string like "\draw[circle] (-0.2,0) -- (4.2,0) node[rectangle] {$x$};", rectangle by default*/
        String[] shapes = new String[]{"circle", "triangle", "rectangle"};
        for (String s: shapes)
            if (list.contains(s)) return s;
        return "rectangle";
    }

    private static String getNodeShape(List<String> list1, List<String> list2) {
        /* Testing for shape by priority in a string like "\draw[circle] (-0.2,0) -- (4.2,0) node[rectangle] {$x$};", rectangle by default*/
        String[] shapes = new String[]{"circle", "triangle", "rectangle"};
        for (String s: shapes)
            if (list2.contains(s)) return s;
        for (String s: shapes)
            if (list1.contains(s)) return s;
        return "rectangle";
    }
}

class Triple<S, T, U> {
	private S coordinates;
	private T options;
	private U label;

    public Triple(S s, T t, U u){
        coordinates = s; options = t; label = u;
    }

	public S getCoordinates() {
		return coordinates;
	}

	public T getOptions() {
		return options;
	}

	public U getLabel() {
		return label;
	}

    @Override
    public String toString() {
        return "Coordinates: " + coordinates.toString() + ", Options: " + options.toString() + ", Label: " + label.toString();
    }
}