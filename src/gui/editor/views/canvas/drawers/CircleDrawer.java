package gui.editor.views.canvas.drawers;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Vector;

import models.TikzCircle;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;
import models.TikzComponent;

public class CircleDrawer extends ComponentDrawer {

    public CircleDrawer() {}

    @Override
    public DrawableTikzComponent toDrawable(TikzComponent component) {
        TikzCircle circle = (TikzCircle)component;
        DrawableTikzComponent drawableComponent = super.toDrawable(circle);

        float size = circle.getRadius() * 2;
        drawableComponent.addShape(new Ellipse2D.Float(0, 0, size, size));
        drawableComponent.setStroke(new BasicStroke(circle.getStroke()));
        drawableComponent.setColor(circle.getColor());
        drawableComponent.setBackground(circle.getBackground());
        return drawableComponent;
    }
}
