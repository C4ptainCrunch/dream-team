package gui.drawers;

import gui.drawables.Drawable;
import gui.drawables.DrawableShape;
import gui.drawables.DrawableText;
import models.TikzCircle;
import constants.Models;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Vector;

public class CircleDrawer extends ComponentDrawer {

    public CircleDrawer(TikzCircle component) {
        this.component = component;
    }

    public TikzCircle getComponent(){
        return (TikzCircle) this.component;
    }

    public Vector<Drawable> toDrawable(){
        Vector<Drawable> vec = super.toDrawable();

        float size = getComponent().getRadius() * 2;
        Drawable shape = new DrawableShape(
                new Ellipse2D.Float(0, 0, size, size),
                new BasicStroke(2),
                new Color(0, 0, 0)
        );

        vec.add(shape);
        return vec;
    }
}
