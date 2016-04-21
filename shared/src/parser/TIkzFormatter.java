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
        Point first = edge.getFromPosition();
        Point second = edge.getToPosition();
        return String.format("\\draw[%s] (%.0f, %.0f) -- (%.0f, %.0f);\n", options, first.x, first.y, second.x, second.y);
    }

    public static String format(TikzComponent c){
        System.out.println("noooo");
        return "";
    }

    public static String format(TikzCircle circle) {
        return tikzSource(circle, String.join(", ", new String[] { "circle", "draw" }));
    }

    public static String format(TikzRectangle rectangle){
        return tikzSource(rectangle, String.join(", ", new String[] { "rectangle", "draw" }));
    }

    public static String format(TikzPolygon polygon){
        return tikzSource(polygon, String.join(", ", new String[] { "regular polygon", "draw" }));
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


