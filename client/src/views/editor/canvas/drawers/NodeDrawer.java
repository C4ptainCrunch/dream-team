package views.editor.canvas.drawers;

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;

import misc.utils.Converter;
import models.tikz.TikzComponent;
import models.tikz.TikzNode;
import utils.Geom;
import views.editor.canvas.drawables.DrawableTikzNode;

/**
 * Created by jhellinckx on 18/04/16.
 */
public abstract class NodeDrawer extends ComponentDrawer {
    @Override
    public DrawableTikzNode toDrawable(TikzComponent component, JComponent panel) {
        DrawableTikzNode drawable = new DrawableTikzNode(component);
        return drawable;
    }

    public Shape getPositionedShape(Shape shape, TikzNode node, JComponent panel) {
        /* First center it before applying the converter. */
        shape = getCenteredShape(shape);
        /*
         * Convert the tikz point of the model to a swing point and translate
         * the Area with the resulting point.
         */
        Point swingPosition = Converter.tikz2swing(node.getPosition(), panel);
        AffineTransform toSwingTransform = new AffineTransform(1, 0, 0, 1, swingPosition.getX(), swingPosition.getY());
        return toSwingTransform.createTransformedShape(shape);
    }

    public Polygon getPositionedShape(Polygon polygon, TikzNode node){
        Rectangle bounds = polygon.getBounds();
        polygon.translate((int)-bounds.getWidth() / 2,(int) -bounds.getHeight() /2);
        polygon.translate(node.getPosition().x, node.getPosition().y);
        return polygon;
    }

    public Polygon getPositionedShape(Polygon polygon, TikzNode node, JComponent panel){
        Rectangle bounds = polygon.getBounds();
        polygon.translate((int)-bounds.getWidth() / 2,(int) -bounds.getHeight() /2);
        Point swingPosition = Converter.tikz2swing(node.getPosition(), panel);
        polygon.translate((int)swingPosition.getX(), (int)swingPosition.getY());
        return polygon;
    }

    public Shape getCenteredShape(Shape shape) {
        Rectangle bounds = shape.getBounds();
        AffineTransform center = new AffineTransform(1, 0, 0, 1, -bounds.getWidth() / 2, -bounds.getHeight() / 2);
        return center.createTransformedShape(shape);
    }

    public Point closestAnchor(TikzNode node, Point point){
        double distance = Double.MAX_VALUE;
        double other_distance;
        Point closest = null;
        for(Point anchor : this.getAnchors(node)){
            other_distance = Geom.euclideanDistance(anchor, point);
            if(other_distance < distance){
                distance = other_distance;
                closest = anchor;
            }
        }
        return closest;
    }

    public abstract java.util.List<Point> getAnchors(TikzNode node);




}
