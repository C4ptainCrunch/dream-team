package views.editor.canvas.drawers;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import misc.utils.Converter;
import models.tikz.TikzCircle;
import models.tikz.TikzComponent;
import models.tikz.TikzNode;
import views.editor.canvas.drawables.DrawableTikzNode;

public class CircleDrawer extends NodeDrawer {

    /**
     * Creates a swing drawable circle object by adding a Circle shape to the
     * list of shapes of the drawable and correctly setting its position.
     *
     * @param component
     *            tikz circle to draw
     * @param panel
     *            panel to draw onto
     * @return drawable with the circle shape
     */
    @Override
    public DrawableTikzNode toDrawable(TikzComponent component, JComponent panel) {
        TikzCircle circle = (TikzCircle) component;
        DrawableTikzNode drawableComponent = super.toDrawable(circle, panel);
        drawableComponent.addShape(getPositionedShape(getAwtCircle(circle), circle, panel));
        return drawableComponent;
    }

    /**
     * Creates an awt circle from the radius of the given TikzCircle. Uses the
     * Ellipse2D awt object to achieve this.
     *
     * @param circle
     *            the TikzCircle to create an awt circle from
     * @return the awt circle shape
     */
    public Ellipse2D getAwtCircle(TikzCircle circle) {
        float radius = (float) Converter.centimetersToPixels(circle.getRadius() * 2);
        return new Ellipse2D.Float(0, 0, radius, radius);
    }

    /**
     * Determines the anchors of the given circle by adding/substracting its
     * radius to the center of the shape.
     *
     * @param node
     *            circle to get the anchors from.
     * @param panel
     *            panel on which the circle is drawn
     * @return a list of swing anchors
     */
    @Override
    public List<Point2D.Float> getAnchors(TikzNode node, JComponent panel) {
        TikzCircle circle = (TikzCircle) node;
        float radius = circle.getRadius();
        Point2D.Float position = circle.getPosition();

        List<Point2D.Float> anchors = new ArrayList<>();
        anchors.add(Converter.tikz2swing(new Point2D.Float(position.x + radius, position.y), panel));
        anchors.add(Converter.tikz2swing(new Point2D.Float(position.x - radius, position.y), panel));
        anchors.add(Converter.tikz2swing(new Point2D.Float(position.x, position.y + radius), panel));
        anchors.add(Converter.tikz2swing(new Point2D.Float(position.x, position.y - radius), panel));
        return anchors;
    }
}
