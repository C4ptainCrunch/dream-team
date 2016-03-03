package gui.drawers;

import gui.drawables.Drawable;
import gui.drawables.DrawableShape;
import gui.drawables.DrawableText;
import models.TikzRectangle;
import constants.Models;

import java.awt.*;
import java.util.Vector;

public class RectangleDrawer extends ComponentDrawer {

    public RectangleDrawer(TikzRectangle component) {
            this.component = component;
    }

    public TikzRectangle getComponent(){
        return (TikzRectangle) this.component;
    }

    public Vector<Drawable> toDrawable(){
        Vector<Drawable> vec = super.toDrawable();

        Drawable shape = new DrawableShape(
                new Rectangle(getComponent().getWidth(), getComponent().getLength()),
                new BasicStroke(2),
                new Color(0, 0, 0)
        );
        vec.add(shape);
        return vec;
    }
}
