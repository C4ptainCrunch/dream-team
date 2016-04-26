package parser;

import constants.Models;
import models.tikz.*;
import java.awt.*;

public class TikzFormatter {
    private TikzFormatter(){}

    public static String tikzSource(TikzNode node, String options){
        Point position = node.getPosition();
        return String.format("\\node[%s](%s) at (%.0f,%.0f){%s};\n", options, node.getReference(), position.getX(), position.getY(), node.getLabel());
    }

    public static String tikzSource(TikzEdge edge, String options){
        String first = edge.getFirstNode().getReference();
        String second = edge.getSecondNode().getReference();
        return String.format("\\draw[%s] (%s) -- (%s);\n", options, first, second);
    }

    public static String format(TikzComponent c){
        return "";
    }

    public static String format(TikzCircle circle) {
        String radiusOption = circle.getRadius() == Models.DEFAULT.LENGTH ? "" : ", radius="+circle.getRadius();
        return tikzSource(circle, String.join(", ",  "circle", "draw" + radiusOption, getCommonOptions(circle), getShapeOptions(circle)));
    }

    public static String format(TikzRectangle rectangle){
        String widthOption = rectangle.getWidth() == Models.DEFAULT.LENGTH ? "" : ", minimum width=" + rectangle.getWidth();
        String heightOption = rectangle.getLength() == Models.DEFAULT.LENGTH ? "" : ", minimum height=" + rectangle.getLength();
        return tikzSource(rectangle, String.join(", ", "rectangle", "draw" + widthOption + heightOption, getCommonOptions(rectangle), getShapeOptions(rectangle)));
    }

    public static String format(TikzPolygon polygon){
        return tikzSource(polygon, String.join(", ", "regular polygon", "draw", "minimum size=" + polygon.getLength(), "regular polygon sides=" + polygon.getSides(), getCommonOptions(polygon), getShapeOptions(polygon) ));
    }

    public static String format(TikzDirectedEdge edge){
        return tikzSource(edge, String.join(", ",  "->", getCommonOptions(edge) ));
    }

    public static String format(TikzUndirectedEdge edge){
        return tikzSource(edge, String.join(", ", getCommonOptions(edge)));
    }

    public static String format(TikzVoid tikzVoid){
        return "";
    }

    public static String format(TikzText text){
        throw new RuntimeException("Not implemented");
    }

    private static String getCommonOptions(TikzComponent comp) {
        return "color=" + TikzColors.ColorToString(comp.getStrokeColor()) + ", line width=" + Integer.toString(comp.getStroke());
    }

    private static String getShapeOptions(TikzShape shape){
        return String.join(", ", "fill=" + TikzColors.ColorToString(shape.getBackgroundColor()));
    }
}


