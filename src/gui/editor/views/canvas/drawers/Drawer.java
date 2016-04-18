package gui.editor.views.canvas.drawers;

import java.util.HashMap;
import java.util.Map;

import gui.editor.views.canvas.drawables.DrawableTikzComponent;
import models.tikz.*;

public final class Drawer {

    private static Map<Class<? extends TikzComponent>, TikzDrawer> drawers = new HashMap<>();

    static {
        drawers.put(TikzTriangle.class, new TriangleDrawer());
        drawers.put(TikzRectangle.class, new RectangleDrawer());
        drawers.put(TikzCircle.class, new CircleDrawer());
        drawers.put(TikzPolygon.class, new PolygonDrawer());
        drawers.put(TikzDirectedEdge.class, new DirectedEdgeDrawer());
        drawers.put(TikzUndirectedEdge.class, new UndirectedEdgeDrawer());
    }

    private Drawer() {
    }

    public static DrawableTikzComponent toDrawable(TikzComponent component) {
        return drawers.get(component.getClass()).toDrawable(component);
    }
}
