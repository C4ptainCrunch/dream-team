package views.editor.canvas.drawers;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

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
        drawableComponent.addShape(getPositionedShape(new Rectangle(rectangle.getWidth(), rectangle.getLength()), rectangle, panel));
        return drawableComponent;

    }

    @Override
    public java.util.List<Point> getAnchors(TikzNode node){
        TikzRectangle rectangle = (TikzRectangle) node;
        int height = rectangle.getLength();
        int width = rectangle.getWidth();
        Point position = rectangle.getPosition();

        java.util.List<Point> anchors = new ArrayList<>();
        anchors.add(new Point(position.x + width/2, position.y));
        anchors.add(new Point(position.x - width/2, position.y));
        anchors.add(new Point(position.x, position.y + height/2));
        anchors.add(new Point(position.x, position.y - height/2));
        return anchors;
    }
}
