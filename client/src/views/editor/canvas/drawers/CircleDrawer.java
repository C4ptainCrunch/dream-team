package views.editor.canvas.drawers;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.*;

import misc.utils.Converter;
import models.tikz.TikzCircle;
import models.tikz.TikzComponent;
import models.tikz.TikzNode;
import models.tikz.TikzShape;
import views.editor.canvas.drawables.DrawableTikzNode;

public class CircleDrawer extends NodeDrawer {

    public CircleDrawer() {
        // this was left intentionally blank
    }

    @Override
    public DrawableTikzNode toDrawable(TikzComponent component, JComponent panel) {
        TikzCircle circle = (TikzCircle) component;
        DrawableTikzNode drawableComponent = super.toDrawable(circle, panel);

        float size = circle.getRadius() * 2;
        drawableComponent.addShape(getPositionedShape(new Ellipse2D.Float(0, 0, size, size), circle, panel));
        return drawableComponent;
    }

    @Override
    public java.util.List<Point> getAnchors(TikzNode node, JComponent panel){
        TikzCircle circle = (TikzCircle) node;
        int radius = circle.getRadius();
        Point position = circle.getPosition();

        java.util.List<Point> anchors = new ArrayList<>();
        anchors.add(Converter.tikz2swing(new Point(position.x + radius, position.y), panel));
        anchors.add(Converter.tikz2swing(new Point(position.x - radius, position.y), panel));
        anchors.add(Converter.tikz2swing(new Point(position.x, position.y + radius), panel));
        anchors.add(Converter.tikz2swing(new Point(position.x, position.y - radius), panel));
        return anchors;
    }
}
