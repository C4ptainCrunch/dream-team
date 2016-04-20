package views.editor.canvas.drawers;

import misc.utils.Converter;
import models.tikz.TikzComponent;
import models.tikz.TikzNode;
import views.editor.canvas.drawables.DrawableTikzNode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by jhellinckx on 18/04/16.
 */
public class NodeDrawer extends ComponentDrawer {
    @Override
    public DrawableTikzNode toDrawable(TikzComponent component, JComponent panel){
        DrawableTikzNode drawable = new DrawableTikzNode(component);
        return drawable;
    }

    public Shape getPositionedShape(Shape shape, TikzNode node, JComponent panel){
         /* First center it before applying the converter. */
        shape = getCenteredShape(shape);
        /* Convert the tikz point of the model to a swing point and translate the Area with the resulting point. */
        Point swingPosition = Converter.tikz2swing(node.getPosition(), panel);
        AffineTransform toSwingTransform = new AffineTransform(1, 0, 0, 1, swingPosition.getX(), swingPosition.getY());
        return toSwingTransform.createTransformedShape(shape);
    }

    public Shape getCenteredShape (Shape shape){
        Rectangle bounds = shape.getBounds();
        AffineTransform center = new AffineTransform(1, 0, 0, 1, - bounds.getWidth()/2, - bounds.getHeight()/2 );
        return center.createTransformedShape(shape);
    }
}
