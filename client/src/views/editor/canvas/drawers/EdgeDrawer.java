package views.editor.canvas.drawers;

import java.awt.*;
import java.awt.geom.Line2D;

import models.tikz.TikzComponent;
import models.tikz.TikzEdge;
import views.editor.canvas.drawables.DrawableTikzEdge;

public abstract class EdgeDrawer extends ComponentDrawer {

    public EdgeDrawer() {
        // this was left intentionally blank
    }

    @Override
    public DrawableTikzEdge toDrawable(TikzComponent component) {
        TikzEdge edge = (TikzEdge) component;
        DrawableTikzEdge drawableComponent = new DrawableTikzEdge(component);
        Point start = edge.getFromPosition();
        Point end = edge.getToPosition();
        drawableComponent.addShape(new Line2D.Float(start, end));
        return drawableComponent;
    }
}
