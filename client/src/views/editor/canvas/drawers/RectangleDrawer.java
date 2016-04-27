package views.editor.canvas.drawers;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import misc.utils.Converter;
import models.tikz.TikzComponent;
import models.tikz.TikzNode;
import models.tikz.TikzRectangle;
import views.editor.canvas.drawables.DrawableTikzNode;

public class RectangleDrawer extends NodeDrawer {

    public RectangleDrawer() {
        // this was left intentionally blank
    }

    @Override
    public DrawableTikzNode toDrawable(TikzComponent component, JComponent panel) {
        TikzRectangle rectangle = (TikzRectangle) component;
        DrawableTikzNode drawableComponent = super.toDrawable(rectangle, panel);
        drawableComponent.addShape(getPositionedShape(getAwtRectangle(rectangle), rectangle, panel));
        return drawableComponent;

    }

    public Rectangle getAwtRectangle(TikzRectangle rectangle){
        return new Rectangle(rectangle.getWidth(), rectangle.getLength());
    }

    @Override
    public java.util.List<Point> getAnchors(TikzNode node, JComponent panel){
        TikzRectangle rectangle = (TikzRectangle) node;
        Shape shape = getPositionedShape(getAwtRectangle(rectangle), rectangle, panel);

        Rectangle bounds = shape.getBounds();
        int x = bounds.x; int y = bounds.y;
        int width = bounds.width;
        int height = bounds.height;
        int widthOffset = width / 2;
        int heightOffset = height / 2;
        java.util.List<Point> anchors = new ArrayList<>();
        anchors.add(new Point(x + widthOffset, y));
        anchors.add(new Point(x + widthOffset, y + height));
        anchors.add(new Point(x, y + heightOffset));
        anchors.add(new Point(x + width, y + heightOffset));
        return anchors;
    }
}
