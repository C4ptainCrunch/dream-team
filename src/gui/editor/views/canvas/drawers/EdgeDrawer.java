package gui.editor.views.canvas.drawers;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Vector;

import models.TikzEdge;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableShape;


public abstract class EdgeDrawer extends ComponentDrawer {

    public EdgeDrawer(TikzEdge component) {
        this.component = component;
    }

    public TikzEdge getComponent(){
        return (TikzEdge) this.component;
    }

    public Vector<Drawable> toDrawable(){
        return toDrawable(this.getComponent().getFromPosition(), this.getComponent().getToPosition());
    }

    public Vector<Drawable> toDrawable(Point start, Point end){
        Vector<Drawable> vec = super.toDrawable();
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
