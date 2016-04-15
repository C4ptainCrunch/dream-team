package gui.editor.views.canvas.drawers;

import java.util.HashMap;

import models.*;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;

public class Drawer {

    private Drawer(){}

    private static HashMap<Class<? extends TikzComponent>, TikzDrawer> drawers = new HashMap<>();

    static {
        drawers.put(TikzTriangle.class, new TriangleDrawer());
        drawers.put(TikzRectangle.class, new RectangleDrawer());
        drawers.put(TikzCircle.class, new CircleDrawer());
        drawers.put(TikzPolygon.class, new PolygonDrawer());
        drawers.put(TikzDirectedEdge.class, new DirectedEdgeDrawer());
        drawers.put(TikzUndirectedEdge.class, new UndirectedEdgeDrawer());
    }

    public static DrawableTikzComponent toDrawable(TikzComponent component){
        return drawers.get(component.getClass()).toDrawable(component);
    }
}
