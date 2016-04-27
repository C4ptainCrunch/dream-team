package views.editor.canvas.drawers;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import misc.utils.Converter;
import models.tikz.TikzComponent;
import models.tikz.TikzNode;
import models.tikz.TikzRectangle;
import views.editor.canvas.drawables.DrawableTikzNode;
import java.util.List;
public class RectangleDrawer extends NodeDrawer {

    public RectangleDrawer() {
        // this was left intentionally blank
    }

    /**
     * Creates a swing drawable rectangle object by adding a Rectangle shape to the list of shapes of the drawable
     * and correctly setting its position.
     * @param component tikz component to draw
     * @param panel panel to draw onto
     * @return drawable with the rectangle shape
     */
    @Override
    public DrawableTikzNode toDrawable(TikzComponent component, JComponent panel) {
        TikzRectangle rectangle = (TikzRectangle) component;
        DrawableTikzNode drawableComponent = super.toDrawable(rectangle, panel);
        drawableComponent.addShape(getPositionedShape(getAwtRectangle(rectangle), rectangle, panel));
        return drawableComponent;

    }

    /**
     * Creates an awt rectangle from the width and height of the given TikzRectangle
     * @param rectangle the TikzRectangle to create an awt rectangle from
     * @return the awt rectangle
     */
    public Rectangle getAwtRectangle(TikzRectangle rectangle){
        return new Rectangle(rectangle.getWidth(), rectangle.getLength());
    }

    /**
     * Determines the anchors of the given node.
     * Uses the shape bounds.
     * @param node rectangle to get the anchors from.
     * @param panel panel on which the rectangle is drawn
     * @return a list of swing anchors
     */
    @Override
    public List<Point> getAnchors(TikzNode node, JComponent panel){
        TikzRectangle rectangle = (TikzRectangle) node;
        Shape shape = getPositionedShape(getAwtRectangle(rectangle), rectangle, panel);

        Rectangle bounds = shape.getBounds();
        int x = bounds.x; int y = bounds.y;
        int width = bounds.width;
        int height = bounds.height;
        int widthOffset = width / 2;
        int heightOffset = height / 2;
        List<Point> anchors = new ArrayList<>();
        anchors.add(new Point(x + widthOffset, y));
        anchors.add(new Point(x + widthOffset, y + height));
        anchors.add(new Point(x, y + heightOffset));
        anchors.add(new Point(x + width, y + heightOffset));
        return anchors;
    }
}
