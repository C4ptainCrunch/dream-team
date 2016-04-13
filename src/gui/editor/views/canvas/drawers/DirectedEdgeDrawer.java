package gui.editor.views.canvas.drawers;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Vector;

import models.TikzDirectedEdge;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableShape;
import static constants.GUI.Config.*;

public class DirectedEdgeDrawer extends EdgeDrawer {
    public DirectedEdgeDrawer(TikzDirectedEdge component) {
        super(component);
    }

    public Vector<Drawable> toDrawable(){
        Vector<Drawable> vec = super.toDrawable();

        Point start = this.getComponent().getToPosition();
        Point end = this.getComponent().getFromPosition();
        double eucDist = Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2));
        double alpha = Math.asin(end.getY()/eucDist);
        double beta = alpha - ARROW_ANGLE;
        double gamma = Math.PI/2 - alpha - ARROW_ANGLE;

        // First arrow head segment.
        double firstDeltaX = Math.cos(beta)*ARROW_LENGTH;
        double firstDeltaY = Math.sin(beta)*ARROW_LENGTH;
        int corrX, corrY;
        corrX = (end.getX() > start.getX()) ? -1 : 1;
        corrY = (end.getY() > start.getY()) ? -1 : 1;
        Drawable firstHeadSegment = new DrawableShape(
                new Line2D.Float(end, new Point((int) (end.getX() + firstDeltaX*corrX), (int)(end.getY() + firstDeltaY*corrY))),
                new BasicStroke(2),
                this.getComponent().getColor(),
                this.getComponent().getColor()
        );

        // Second arrow head segment.
        double secondDeltaX = Math.sin(gamma)*ARROW_LENGTH;
        double secondDeltaY = Math.cos(gamma)*ARROW_LENGTH;
        Drawable secondHeadSegment = new DrawableShape(
                new Line2D.Float(end, new Point((int) (end.getX() + secondDeltaX*corrX), (int)(end.getY() + secondDeltaY*corrY))),
                new BasicStroke(2),
                this.getComponent().getColor(),
                this.getComponent().getColor()
        );

        vec.add(firstHeadSegment);
        vec.add(secondHeadSegment);

        return vec;
    }


    public TikzDirectedEdge getComponent(){
        return (TikzDirectedEdge) super.getComponent();
    }
}
