package gui.editor.views.canvas.drawers;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Vector;

import models.TikzEdge;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;


public abstract class EdgeDrawer extends ComponentDrawer {

    public EdgeDrawer() {}

    public DrawableTikzComponent toDrawable(TikzEdge edge){
        Point start = edge.getFromPosition();
        Point end = edge.getToPosition();
        DrawableTikzComponent drawableComponent = super.toDrawable(edge);
        drawableComponent.addShape( new Line2D.Float(start, end));
        drawableComponent.setStroke( new BasicStroke(2));
        drawableComponent.setColor(edge.getColor());
        drawableComponent.setBackground(edge.getColor());
        return drawableComponent;
    }
}
