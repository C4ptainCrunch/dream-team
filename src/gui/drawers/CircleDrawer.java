package gui.drawers;

import gui.drawables.Drawable;
import gui.drawables.DrawableShape;
import gui.drawables.DrawableText;
import models.TikzCircle;
import constants.Models;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Vector;

public class CircleDrawer implements Drawer {
    private TikzCircle tikzCircle;

    public CircleDrawer(TikzCircle tikzCircle) {
        this.tikzCircle = tikzCircle;
    }

    public Vector<Drawable> toDrawable(){
        Vector<Drawable> vec = new Vector<>();

        float size = this.tikzCircle.getRadius() * 2;
        Drawable shape = new DrawableShape(
                new Ellipse2D.Float(0, 0, size, size),
                new BasicStroke(2),
                new Color(0, 0, 0)
        );

        vec.add(shape);

        if(this.tikzCircle.getLabel() != Models.DEFAULT.LABEL){
            Drawable text = new DrawableText(
                    this.tikzCircle.getLabel(),
                    new Point(15, 20)
            );
            vec.add(text);
        }
        return vec;
    }
}
