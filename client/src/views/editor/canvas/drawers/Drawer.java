package views.editor.canvas.drawers;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import models.tikz.*;
import views.editor.canvas.drawables.DrawableTikzComponent;

public final class Drawer {

    /**
     * Maps each Tikz model class to its appropriate drawer.
     */
    private static Map<Class<? extends TikzComponent>, TikzDrawer> drawers = new HashMap<>();

    static {
        drawers.put(TikzRectangle.class, new RectangleDrawer());
        drawers.put(TikzCircle.class, new CircleDrawer());
        drawers.put(TikzPolygon.class, new PolygonDrawer());
        drawers.put(TikzDirectedEdge.class, new DirectedEdgeDrawer());
        drawers.put(TikzUndirectedEdge.class, new UndirectedEdgeDrawer());
    }

    private Drawer() {
    }

    /**
     * Create a drawable swing object from a tikz component that has to be drawn on the given panel.
     * @param component the tikz component to draw
     * @param panel the panel to draw onto
     * @return the swing drawalbe object
     */
    public static DrawableTikzComponent toDrawable(TikzComponent component, JComponent panel) {
        TikzDrawer componentDrawer = drawers.get(component.getClass());
        return componentDrawer.toDrawable(component, panel);
    }

    public static Point2D.Float closestAnchor(TikzNode node, Point2D.Float point, JComponent panel){
        NodeDrawer nodeDrawer = (NodeDrawer) drawers.get(node.getClass());
        return nodeDrawer.closestAnchor(node, point, panel);
    }
}
