package gui.drawers;

import com.sun.org.apache.xpath.internal.operations.Mod;
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

    public Vector<Drawable> draw(){
        Vector<Drawable> vec = new Vector<Drawable>();
        vec.add(new DrawableShape(new Rectangle(100, 20)));
        if(this.tikzRectangle.getLabel() != Models.DEFAULT.LABEL){
            vec.add(new DrawableText(this.tikzRectangle.getLabel(), new Point(5, 5)));
        }
        return vec;
    }
}
