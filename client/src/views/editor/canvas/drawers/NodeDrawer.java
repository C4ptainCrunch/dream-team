package views.editor.canvas.drawers;

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;

import misc.utils.Converter;
import models.tikz.TikzComponent;
import models.tikz.TikzNode;
import utils.Geom;
import views.editor.canvas.drawables.DrawableTikzNode;
import java.util.List;

public abstract class NodeDrawer extends ComponentDrawer {
    @Override
    public DrawableTikzNode toDrawable(TikzComponent component, JComponent panel) {
        DrawableTikzNode drawable = new DrawableTikzNode(component);
        return drawable;
    }

    /**
     * Set the position of the given shape. uses swing coordinates. The shape
     * is centered and translated by the node coordinates converted to swing coordinates
     * @param shape shape to position.
     * @param node node corresponding to the shape
     * @param panel panel to draw the shape onto
     * @return the positioned shape
     */
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


    /**
     * Centers the shape
     * @param shape shape to center
     * @return centered shape
     */
    public Shape getCenteredShape(Shape shape) {
        Rectangle bounds = shape.getBounds();
        AffineTransform center = new AffineTransform(1, 0, 0, 1, -bounds.getWidth() / 2, -bounds.getHeight() / 2);
        return center.createTransformedShape(shape);
    }


    /**
     * Determine the closest node anchor from the point
     * @param node the node to get the anchors from
     * @param point point to get the node closest anchor from
     * @param panel panel on whoch the node is drawn
     * @return the node closest anchor from the point
     */
    public Point closestAnchor(TikzNode node, Point point, JComponent panel){
        double distance = Double.MAX_VALUE;
        double other_distance;
        Point closest = null;
        point = Converter.tikz2swing(point, panel);
        for(Point anchor : this.getAnchors(node, panel)){
            other_distance = Geom.euclideanDistance(anchor, point);
            if(other_distance < distance){
                distance = other_distance;
                closest = anchor;
            }
        }
        return closest;
    }

    public abstract List<Point> getAnchors(TikzNode node, JComponent panel);




}
