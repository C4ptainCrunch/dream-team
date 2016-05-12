package parser;

import java.awt.geom.Point2D;
import java.util.*;

import models.tikz.*;
import constants.Models;

public class TikzFormatter {
    private TikzFormatter() {
    }

    /**
     * Format a TikzNode to create a String (specific implementation of toString
     * method).
     *
     * @param node
     *            The TikzNode to format.
     * @param options
     *            The TikzNode options (such as the node's color).
     * @return The corresponding formatted String.
     */

    public static String tikzSource(TikzNode node, String options) {
        Point2D.Float position = node.getPosition();
        return String.format(Locale.US, "\\node[%s](%s) at (%.3f,%.3f){%s};\n", options, node.getReference(), position.getX(),
                position.getY(), node.getLabel());
    }

    /**
     * Format a TikzEdge to create a String (specific implementation of toString
     * method).
     *
     * @param edge
     *            The TikzEdge to format.
     * @param options
     *            The TikzNode options (such as the edge's color).
     * @return The corresponding formatted String.
     */

    public static String tikzSource(TikzEdge edge, String options) {
        String first = edge.getFirstNode().getReference();
        String second = edge.getSecondNode().getReference();
        return String.format("\\draw[%s] (%s) -- (%s);\n", options, first, second);
    }

    public static String format(TikzComponent c) {
        return "";
    }

    /**
     * Format a TikzCircle to create a String (specific implementation of
     * toString method).
     *
     * @param circle
     *            The TikzCircle to format.
     * @return The corresponding formatted String.
     */

    public static String format(TikzCircle circle) {
        List<String> options = new ArrayList<>(Arrays.asList("circle", "draw"));
        if (circle.getRadius() != Models.DEFAULT.LENGTH) {
            options.add("radius=" + circle.getRadius());
        }
        options.addAll(getCommonOptions(circle));
        options.addAll(getShapeOptions(circle));
        return tikzSource(circle, String.join(", ", options));
    }

    /**
     * Format a TikzRectangle to create a String (specific implementation of
     * toString method).
     *
     * @param rectangle
     *            The TikzRectangle to format.
     * @return The corresponding formatted String.
     */

    public static String format(TikzRectangle rectangle) {
        List<String> options = new ArrayList<>(Arrays.asList("rectangle", "draw"));
        if (rectangle.getWidth() != Models.DEFAULT.LENGTH) {
            options.add("minimum width=" + Float.toString(rectangle.getWidth()));
        }
        if (rectangle.getLength() != Models.DEFAULT.LENGTH) {
            options.add("minimum height=" + Float.toString(rectangle.getLength()));
        }
        options.addAll(getCommonOptions(rectangle));
        options.addAll(getShapeOptions(rectangle));
        return tikzSource(rectangle, String.join(", ", options));
    }

    /**
     * Format a TikzPolygon to create a String (specific implementation of
     * toString method).
     *
     * @param polygon
     *            The TikzPolygon to format.
     * @return The corresponding formatted String.
     */

    public static String format(TikzPolygon polygon) {
        ArrayList<String> options = new ArrayList<>(Arrays.asList("regular polygon", "draw"));
        if (polygon.getLength() != Models.DEFAULT.LENGTH) {
            options.add("minimum size=" + Float.toString(polygon.getLength()));
        }
        if (polygon.getSides() != Models.DEFAULT.SIDES) {
            options.add("regular polygon sides=" + Float.toString(polygon.getSides()));
        }
        options.addAll(getCommonOptions(polygon));
        options.addAll(getShapeOptions(polygon));
        return tikzSource(polygon, String.join(", ", options));
    }

    /**
     * Format a TikzDirectedEdge to create a String (specific implementation of
     * toString method).
     *
     * @param edge
     *            The TikzCircle to format.
     * @return The corresponding formatted String.
     */

    public static String format(TikzDirectedEdge edge) {
        List<String> options = new ArrayList<>(Collections.singletonList("->"));
        options.addAll(getCommonOptions(edge));
        return tikzSource(edge, String.join(", ", options));
    }

    /**
     * Format a TikzUndirectedEdge to create a String (specific implementation
     * of toString method).
     *
     * @param edge
     *            The TikzCircle to format.
     * @return The corresponding formatted String.
     */

    public static String format(TikzUndirectedEdge edge) {
        return tikzSource(edge, String.join(", ", getCommonOptions(edge)));
    }

    public static String format(TikzVoid tikzVoid) {
        return "";
    }

    public static String format(TikzText text) {
        throw new RuntimeException("Not implemented");
    }

    private static List<String> getCommonOptions(TikzComponent comp) {
        ArrayList<String> options = new ArrayList<>();
        if (!comp.getStrokeColor().equals(Models.DEFAULT.COLOR)) {
            options.add("color=" + TikzColors.ColorToString(comp.getStrokeColor()));
        }
        if (comp.getStroke() != Models.DEFAULT.STROKE) {
            options.add("line width=" + Integer.toString(comp.getStroke()));
        }
        return options;
    }

    private static List<String> getShapeOptions(TikzShape shape) {
        ArrayList<String> options = new ArrayList<>();
        if (!shape.getBackgroundColor().equals(Models.DEFAULT.BACKGROUND_COLOR)) {
            options.add("fill=" + TikzColors.ColorToString(shape.getBackgroundColor()));
        }
        return options;
    }
}
