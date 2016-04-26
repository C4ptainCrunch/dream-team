package parser;

import models.tikz.*;
import java.awt.*;

/**
 * Created by jhellinckx on 21/04/16.
 */
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
        return tikzSource(circle, String.join(", ",  "circle", "draw", "radius=" + circle.getRadius(), getCommonOptions(circle) ));
    }

    private static String getCommonOptions(TikzComponent comp) {
        return "color=" + TikzColors.ColorToString(comp.getColor()); //TODO
    }

    public static String format(TikzRectangle rectangle){
        return tikzSource(rectangle, String.join(", ", "rectangle", "draw" , "minimum width=" + rectangle.getWidth(), "minimum height=" + rectangle.getLength()));
    }

    public static String format(TikzPolygon polygon){
        return tikzSource(polygon, String.join(", ", "regular polygon", "draw", "minimum size=" + polygon.getLength(), "regular polygon sides=" + polygon.getSides() ));
    }

    public static String format(TikzDirectedEdge edge){
        return tikzSource(edge, String.join(", ", new String[] { "->" }));
    }

    public static String format(TikzUndirectedEdge edge){
        return tikzSource(edge, String.join(", ", new String[] {}));
    }

    public static String format(TikzVoid tikzVoid){
        return "";
    }

    public static String format(TikzText text){
        throw new RuntimeException("Not implemented");
    }
}


