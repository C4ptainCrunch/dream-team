package gui.editor.views.canvas.drawers;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Vector;

import models.TikzDirectedEdge;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableShape;

public class DirectedEdgeDrawer extends EdgeDrawer {
    public DirectedEdgeDrawer(TikzDirectedEdge component) {
        super(component);
    }

    public Vector<Drawable> toDrawable(){
        Vector<Drawable> vec = super.toDrawable();

        Point start = this.getComponent().getFirstNode().getPosition();
        Point end = this.getComponent().getSecondNode().getPosition();

        Point center = new Point(
                (int) (start.getX() + end.getX()) / 2,
                (int) (start.getY() + end.getY()) / 2
        );

        Drawable shape = new DrawableShape(
                new Ellipse2D.Float((float) center.getX(), (float)  center.getY(), 10, 10),
                new BasicStroke(2),
                this.getComponent().getColor(),
                this.getComponent().getColor(),
                true
        );
        vec.add(shape);

        return vec;
    }

    public TikzDirectedEdge getComponent(){
        return (TikzDirectedEdge) super.getComponent();
    }
}
