package views.editor.canvas.drawers;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import misc.utils.Converter;
import models.tikz.TikzCircle;
import models.tikz.TikzComponent;
import views.editor.canvas.drawables.DrawableTikzComponent;

import java.awt.geom.Ellipse2D;

import models.tikz.TikzCircle;
import models.tikz.TikzComponent;
import views.editor.canvas.drawables.DrawableTikzNode;

import javax.swing.*;

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
}
