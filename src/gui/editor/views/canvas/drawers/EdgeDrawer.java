package gui.editor.views.canvas.drawers;

import java.awt.*;
import java.awt.geom.Line2D;

import models.TikzComponent;
import models.TikzEdge;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;


public abstract class EdgeDrawer extends ComponentDrawer {

    public EdgeDrawer() {}

    @Override
    public DrawableTikzComponent toDrawable(TikzComponent component) {
        TikzEdge edge = (TikzEdge)component;
        DrawableTikzComponent drawableComponent = super.toDrawable(edge);
        Point start = edge.getFromPosition();
        Point end = edge.getToPosition();
        drawableComponent.addShape(new Line2D.Float(start, end));
        drawableComponent.setBackground(edge.getColor());
        return drawableComponent;
    }
}
