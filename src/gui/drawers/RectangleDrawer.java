package gui.drawers;

import gui.drawables.Drawable;
import gui.drawables.DrawableShape;
import gui.drawables.DrawableText;
import models.TikzRectangle;
import constants.Models;

import java.awt.*;
import java.util.Vector;

public class RectangleDrawer implements Drawer {
    private TikzRectangle tikzRectangle;

    public RectangleDrawer(TikzRectangle tikzRectangle) {
            this.tikzRectangle = tikzRectangle;
    }

    public Vector<Drawable> toDrawable(){
        Vector<Drawable> vec = new Vector<>();

        Drawable shape = new DrawableShape(
                new Rectangle(this.tikzRectangle.getWidth(), this.tikzRectangle.getLength()),
                new BasicStroke(2),
                new Color(0, 0, 0)
        );

        vec.add(shape);

        if(this.tikzRectangle.getLabel() != Models.DEFAULT.LABEL){
            Drawable text = new DrawableText(
                    this.tikzRectangle.getLabel(),
                    new Point(15, 20)
            );
            vec.add(text);
        }
        return vec;
    }
}
