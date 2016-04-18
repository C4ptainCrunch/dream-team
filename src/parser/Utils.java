package parser;

import constants.Models;
import models.tikz.*;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;

public class Utils {
    private static final String[] shapes = new String[] { "rectangle", "circle", "ellipse", "circle split",
            "forbidden sign", "diamond", "cross out", "strike out", "regular polygon" };
    private final static Set<String> rectangles = new HashSet<>(Arrays.asList("rectangle", "diamond"));
    private final static Set<String> circles = new HashSet<>(
            Arrays.asList("circle", "ellipse", "circle split", "forbidden sign"));
    private final static Set<String> polygons = new HashSet<>(Arrays.asList("regular polygon", "star"));
    private final static Set<String> colors = new HashSet<>(Arrays.asList("red", "green", "blue", "cyan ", "magenta", "yellow", "black", "gray", "darkgray", "lightgray", "brown", "lime", "olive", "orange", "pink", "purple", "teal", "violet", "white"));

    private static Optional<String> getNodeShape(Map<String, String> m1) {
        for (String s: shapes) {
            if (m1.containsKey(s)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    private static Optional<String> getNodeShape(Map<String, String> m1, Map<String, String> m2) {
        final Optional<String> nodeShape = getNodeShape(m1);
        return nodeShape.isPresent() ? nodeShape : getNodeShape(m2);
    }

    private static Optional<String> getColorShape(Map<String, String> map) {
        if (map.containsKey("color")) {return Optional.of(map.get("color"))}
        for (String c : colors) {
            if (map.containsKey(c)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    private static Optional<String> getColorShape(Map<String, String> m1, Map<String, String> m2) {
        Optional<String> color = getColorShape(m1);
        return color.isPresent() ? color : getColorShape(m2);
    }

    private static Color stringToColor(String color) {
        try {
            Field f = Class.forName("java.awt.Color").getField(color);
            return (Color) f.get(null);
        } catch (Exception e) {return Models.DEFAULT.COLOR;}
    }
    
    private static Optional<String> getOptionString(String opt, Map<String, String> m1, Map<String, String> m2) {
        if (m1.containsKey(opt)) {return Optional.of(m1.get(opt));}
        if (m2.containsKey(opt)) {return Optional.of(m2.get(opt));}
        return Optional.empty();
    }

    private static Optional<Integer> getOptionInt((String opt, Map<String, String> m1, Map<String, String> m2) {
        if (m1.containsKey(opt)) {return Optional.of(Integer.parseInt(m1.get(opt)));}
        if (m2.containsKey(opt)) {return Optional.of(Integer.parseInt(m2.get(opt)));}
        return Optional.empty();
    }

    private static Optional<Integer> getOptionStroke(Map<String, String> map) {
        if (map.containsKey("line width")) { return Optional.of(Math.round(Float.parseFloat(map.get("line width")))); }
        if (map.containsKey("ultra thin")) { return Optional.of(1); }
        if (map.containsKey("very thin")) { return Optional.of(2); }
        if (map.containsKey("thin")) { return Optional.of(4); }
        if (map.containsKey("semithick")) { return Optional.of(6); }
        if (map.containsKey("thick")) { return Optional.of(8); }
        if (map.containsKey("very thick")) { return Optional.of(12); }
        if (map.containsKey("ultra thick")) { return Optional.of(16); }
        return Optional.empty();
    }

    private static Optional<Integer> getOptionStroke(Map<String, String> m1, Map<String, String> m2) {
        final Optional<Integer> optionStroke = getOptionStroke(m1);
        return optionStroke.isPresent() ? optionStroke : getOptionStroke(m2);
    }

    private static TikzNode createNode(Map<String, String> defaultOptions, DestructuredNode node){
        final String shape = getNodeShape(defaultOptions, node.getOptions()).orElse("void");
        final String color = getColorShape(defaultOptions, node.getOptions()).orElse("black");
        TikzNode res;
        if (rectangles.contains(shape)){
            int width = getOptionInt("minimum width", defaultOptions, node.getOptions()).orElse(Models.DEFAULT.LENGTH);
            int height = getOptionInt("minimum height", defaultOptions, node.getOptions()).orElse(Models.DEFAULT.LENGTH);
            res = new TikzRectangle(width, height);
        } else if (circles.contains(shape)){
            int radius = getOptionInt("radius", defaultOptions, node.getOptions()).orElse(Models.DEFAULT.LENGTH);
            res = new TikzCircle(radius);
        } else if (polygons.contains(shape)){
            int size = getOptionInt("minimum size", defaultOptions, node.getOptions()).orElse(Models.DEFAULT.LENGTH);
            int sides = getOptionInt("regular polygon sides", defaultOptions, node.getOptions()).orElse(Models.DEFAULT.SIDES);
            res = new TikzPolygon(size, sides);
        } else { res = new TikzVoid(); }
        res.setPosition(node.getCoordinates());
        res.setLabel(node.getLabel());
        res.setColor(stringToColor(color));
        res.setStroke(getOptionStroke(defaultOptions, node.getOptions()).orElse(Models.DEFAULT.STROKE));
        return res; //TODO: ref
    }

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

