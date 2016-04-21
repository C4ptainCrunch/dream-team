package parser;

import com.sun.tools.corba.se.idl.constExpr.Not;
import models.tikz.*;
import sun.tools.tree.BinaryLogicalExpression;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * Created by jhellinckx on 21/04/16.
 */
public class TikzFormatter {
    private TikzFormatter(){}

    private static final Map<Class<? extends TikzComponent>, TikzOptionsFetcher> optionsFetchers = new HashMap<>();

    static{
        optionsFetchers.put(TikzCircle.class, new TikzCircleOptionsFetcher());
        optionsFetchers.put(TikzRectangle.class, new TikzRectangleOptionsFetcher());
        optionsFetchers.put(TikzPolygon.class, new TikzPolygonOptionsFetcher());
        optionsFetchers.put(TikzVoid.class, new TikzVoidOptionsFetcher());
        optionsFetchers.put(TikzText.class, new TikzTextOptionsFetcher());
        optionsFetchers.put(TikzDirectedEdge.class, new TikzDirectedEdgeOptionsFetcher());
        optionsFetchers.put(TikzUndirectedEdge.class, new TikzUndirectedEdgeFetcher());

    }

    public static String format(TikzComponent component) {
        return component.isNode() ? format((TikzNode)component) : format((TikzEdge)component);
    }

    public static String format(TikzNode node) {
        if(node.isVoid()){
            return "";
        }
        Point position = node.getPosition();
        final TikzOptionsFetcher tikzOptionsFetcher = optionsFetchers.get(node.getClass());
        return String.format("\\node[%s](%s) at (%.0f,%.0f){%s};\n", tikzOptionsFetcher.getOptions(node), node.getReference(), position.getX(), position.getY(), node.getLabel());
    }

    public static String format(TikzEdge edge) {
        Point first = edge.getFromPosition();
        Point second = edge.getToPosition();
        final TikzOptionsFetcher tikzOptionsFetcher = optionsFetchers.get(edge.getClass());
        return String.format("\\draw[%s] (%.0f, %.0f) -- (%.0f, %.0f);\n", tikzOptionsFetcher.getOptions(edge), first.x, first.y, second.x, second.y);
    }

}

interface TikzOptionsFetcher{
    String getOptions(TikzComponent component);
}

class TikzCircleOptionsFetcher implements TikzOptionsFetcher{
    @Override
    public String getOptions(TikzComponent component){
        TikzCircle circle = (TikzCircle)component;
        return String.join(", ", new String[] { "circle", "draw" });
    }
}

class TikzRectangleOptionsFetcher implements TikzOptionsFetcher{
    @Override
    public String getOptions(TikzComponent component){
        TikzRectangle rectangle = (TikzRectangle) component;
        return String.join(", ", new String[] { "rectangle", "draw" });
    }
}

class TikzPolygonOptionsFetcher implements TikzOptionsFetcher{
    @Override
    public String getOptions(TikzComponent component){
        TikzPolygon polygon = (TikzPolygon)component;
        return String.join(", ", new String[] { "regular polygon", "draw" });
    }
}

class TikzUndirectedEdgeFetcher implements TikzOptionsFetcher{
    @Override
    public String getOptions(TikzComponent component){
        TikzUndirectedEdge edge = (TikzUndirectedEdge) component;
        return String.join(", ", new String[] {});
    }
}

class TikzDirectedEdgeOptionsFetcher implements TikzOptionsFetcher{
    @Override
    public String getOptions(TikzComponent component){
        TikzDirectedEdge edge = (TikzDirectedEdge) component;
        return String.join(", ", new String[] { "->" });
    }
}

class TikzVoidOptionsFetcher implements TikzOptionsFetcher{
    @Override
    public String getOptions(TikzComponent component){
        return "";
    }
}

class TikzTextOptionsFetcher implements TikzOptionsFetcher{
    @Override
    public String getOptions(TikzComponent component){
       throw new RuntimeException("NOT IMPLEMENTED");
    }
}


