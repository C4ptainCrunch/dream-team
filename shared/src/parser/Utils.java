package parser;

import java.util.*;

import models.tikz.*;
import constants.Models;

class Utils {
    private static final String[] shapes = new String[] { "rectangle", "circle", "ellipse", "circle split", "forbidden sign", "diamond",
            "cross out", "strike out", "regular polygon" };
    private final static Set<String> rectangles = new HashSet<>(Arrays.asList("rectangle", "diamond"));
    private final static Set<String> circles = new HashSet<>(Arrays.asList("circle", "ellipse", "circle split", "forbidden sign"));
    private final static Set<String> polygons = new HashSet<>(Arrays.asList("regular polygon", "star"));
    private final static Set<String> colors = new HashSet<>(Arrays.asList("red", "green", "blue", "cyan ", "magenta", "yellow", "black",
            "gray", "darkgray", "lightgray", "brown", "lime", "olive", "orange", "pink", "purple", "teal", "violet", "white"));


        /**
     * private default constructor
     */
    private Utils() {
    };

    /**
     * find the shape of a node in a map of options
     *
     * @param m1
     *            map of options
     * @return an optional name of a shape
     */
    private static Optional<String> getNodeShape(Map<String, String> m1) {
        for (String s : shapes) {
            if (m1.containsKey(s)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    /**
     * find the shape of a node in maps of options
     *
     * @param m1
     *            map of default options
     * @param m2
     *            map of options
     * @return an optional name of a shape
     */
    private static Optional<String> getNodeShape(Map<String, String> m1, Map<String, String> m2) {
        final Optional<String> nodeShape = getNodeShape(m1);
        return nodeShape.isPresent() ? nodeShape : getNodeShape(m2);
    }

    /**
     * fetch color from a map of options
     *
     * @param map
     *            map of options
     * @return an optional name of a color
     */
    private static Optional<String> getBackgroundColorShape(Map<String, String> map) {
        if (map.containsKey("fill")) {
            return Optional.of(map.get("fill"));
        }
        for (String c : colors) {
            if (map.containsKey(c)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    private static Optional<String> getStrokeColorShape(Map<String, String> map){
        if (map.containsKey("color")) {
            return Optional.of(map.get("color"));
        }
        for (String c : colors) {
            if (map.containsKey(c)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    /**
     * fetch color from maps of options
     *
     * @param m1
     *            map of default options
     * @param m2
     *            map of options
     * @return an optional name of a color
     */
    private static Optional<String> getStrokeColorShape(Map<String, String> m1, Map<String, String> m2) {
        Optional<String> color = getStrokeColorShape(m1);
        return color.isPresent() ? color : getStrokeColorShape(m2);
    }

    private static Optional<String> getBackgroundColorShape(Map<String, String> m1, Map<String, String> m2) {
        Optional<String> color = getBackgroundColorShape(m1);
        return color.isPresent() ? color : getBackgroundColorShape(m2);
    }

    /**
     * Find an option from maps of options which returns a string to get it's
     * value
     *
     * @param opt
     *            option name
     * @param m1
     *            default map
     * @param m2
     *            map
     * @return an optional string result
     */
    private static Optional<String> getOptionString(String opt, Map<String, String> m1, Map<String, String> m2) {
        if (m1.containsKey(opt)) {
            return Optional.of(m1.get(opt));
        }
        if (m2.containsKey(opt)) {
            return Optional.of(m2.get(opt));
        }
        return Optional.empty();
    }

    /**
     * Find an option from maps of options which returns an integer to get it's
     * value
     *
     * @param opt
     *            option name
     * @param m1
     *            default map
     * @param m2
     *            map
     * @return an optional integer result
     */
    private static Optional<Integer> getOptionInt(String opt, Map<String, String> m1, Map<String, String> m2) {
        if (m1.containsKey(opt)) {
            return Optional.of(Integer.parseInt(m1.get(opt)));
        }
        if (m2.containsKey(opt)) {
            return Optional.of(Integer.parseInt(m2.get(opt)));
        }
        return Optional.empty();
    }

    /**
     * Find the stroke from a map of default options or a map of options
     *
     * @param map
     *            map of options
     * @return an optional stroke
     */
    private static Optional<Integer> getOptionStroke(Map<String, String> map) {
        if (map.containsKey("line width")) {
            return Optional.of(Math.round(Float.parseFloat(map.get("line width"))));
        }
        if (map.containsKey("ultra thin")) {
            return Optional.of(1);
        }
        if (map.containsKey("very thin")) {
            return Optional.of(2);
        }
        if (map.containsKey("thin")) {
            return Optional.of(4);
        }
        if (map.containsKey("semithick")) {
            return Optional.of(6);
        }
        if (map.containsKey("thick")) {
            return Optional.of(8);
        }
        if (map.containsKey("very thick")) {
            return Optional.of(12);
        }
        if (map.containsKey("ultra thick")) {
            return Optional.of(16);
        }
        return Optional.empty();
    }

    /**
     * Find the stroke from a map of default options or a map of options
     *
     * @param m1
     *            map of default options
     * @param m2
     *            map of options
     * @return an optional stroke
     */
    private static Optional<Integer> getOptionStroke(Map<String, String> m1, Map<String, String> m2) {
        final Optional<Integer> optionStroke = getOptionStroke(m1);
        return optionStroke.isPresent() ? optionStroke : getOptionStroke(m2);
    }


    /**
     * Creates a tikz node from a destructured node and a list of options
     * defining this node
     *
     * @param defaultOptions
     *            The options defining the node
     * @param node
     *            The destructured node
     * @return The tikz node created from the destructured node and the option
     *         list
     */
    public static TikzNode createNode(Map<String, String> defaultOptions, DestructuredNode node) {
        final String shape = getNodeShape(defaultOptions, node.getOptions()).orElse("void");
        final String strokeColor = getStrokeColorShape(defaultOptions, node.getOptions()).orElse("black");
        final String backgroundColor = getBackgroundColorShape(defaultOptions, node.getOptions()).orElse("black"); //TODO CONSTANTS IN DEFAULT FOR BLACK
        final int stroke = getOptionStroke(defaultOptions, node.getOptions()).orElse(Models.DEFAULT.STROKE);
        TikzShape res;
        if (rectangles.contains(shape)) {
            int width = getOptionInt("minimum width", defaultOptions, node.getOptions()).orElse(Models.DEFAULT.LENGTH);
            int height = getOptionInt("minimum height", defaultOptions, node.getOptions()).orElse(Models.DEFAULT.LENGTH);
            res = new TikzRectangle(width, height);
        } else if (circles.contains(shape)) {
            int radius = getOptionInt("radius", defaultOptions, node.getOptions()).orElse(Models.DEFAULT.LENGTH);
            res = new TikzCircle(radius);
        } else if (polygons.contains(shape)) {
            int size = getOptionInt("minimum size", defaultOptions, node.getOptions()).orElse(Models.DEFAULT.LENGTH);
            int sides = getOptionInt("regular polygon sides", defaultOptions, node.getOptions()).orElse(Models.DEFAULT.SIDES);
            res = new TikzPolygon(size, sides);
        } else{
            return null;
        }
        res.setPosition(node.getCoordinates());
        res.setLabel(node.getLabel());
        res.setStrokeColor(TikzColors.StringToColor(strokeColor));
        res.setBackgroundColor(TikzColors.StringToColor(backgroundColor));
        res.setStroke(stroke);
        final Optional<String> ref = node.getRef();
        if (ref.isPresent()) {
            res.setReference(ref.get());
        }
        return res;
    }

    /**
     * Creates a tikz node from a destructured node
     *
     * @param node
     *            The destructured node
     * @return the tikz node created from the destructured node
     */
    public static TikzNode createNode(DestructuredNode node) {
        return createNode(new HashMap<>(), node);
    }

    public static TikzEdge createEdge(HashMap<String, String> options, TikzNode n1, TikzNode n2) {
        final String color = getBackgroundColorShape(options).orElse(Models.DEFAULT.COLOR.toString());
        final int stroke = getOptionStroke(options).orElse(Models.DEFAULT.STROKE);
        TikzEdge res;
        switch (isDirected(options)) {
        case RIGHTDIRECTED:
            res = new TikzDirectedEdge(n1, n2);
            break;
        case LEFTDIRECTED:
            res = new TikzDirectedEdge(n2, n1);
            break;
        default:
            res = new TikzUndirectedEdge(n1, n2);
            break;
        }
        res.setStrokeColor(TikzColors.StringToColor(color));
        res.setStroke(stroke);
        return res;
    }

    /**
     * Returns a string defining the type of the edges linking the nodes in a
     * tikz command.
     *
     * @param options
     *            The options map of the tikz command
     * @return whether the edges are left/right directed or undirected
     */
    public static EDGEDIR isDirected(Map<String, String> options) {
        if (options.containsKey("->")) {
            return EDGEDIR.RIGHTDIRECTED;
        } else if (options.containsKey("<-")) {
            return EDGEDIR.LEFTDIRECTED;
        } else {
            return EDGEDIR.UNDIRECTED;
        }
    }

private enum EDGEDIR {
        UNDIRECTED, RIGHTDIRECTED, LEFTDIRECTED
    }
}
