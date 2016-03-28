package gui.editor.views.canvas.drawers;

import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableShape;
import models.TikzEdge;

import java.awt.*;

import java.awt.geom.Line2D;
import java.util.Vector;


public abstract class EdgeDrawer extends ComponentDrawer {

    public EdgeDrawer(TikzEdge component) {
        this.component = component;
    }

    public TikzEdge getComponent(){
        return (TikzEdge) this.component;
    }

    public Vector<Drawable> toDrawable(){
        Vector<Drawable> vec = super.toDrawable();

        Point start = this.getComponent().getFirstNode().getPosition();
        Point end = this.getComponent().getSecondNode().getPosition();

        Drawable shape = new DrawableShape(
                new Line2D.Float(start, end),
                new BasicStroke(2),
                this.getComponent().getColor(),
                this.getComponent().getColor()
        );

        vec.add(shape);
        return vec;
    }

    public Point getCenter(){
        return new Point(0, 0);
    }
}
