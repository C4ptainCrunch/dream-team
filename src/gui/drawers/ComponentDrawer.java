package gui.drawers;

import constants.Models;
import gui.drawables.Drawable;
import gui.drawables.DrawableText;
import models.TikzComponent;

import java.awt.*;
import java.util.Vector;


public abstract class ComponentDrawer implements Drawer {
    public TikzComponent component;

    public Vector<Drawable> toDrawable(){
        Vector<Drawable> vec = new Vector<>();

        if(this.component.getLabel() != Models.DEFAULT.LABEL){
            Drawable text = new DrawableText(
                    this.component.getLabel(),
                    new Point(15, 20)
            );
            vec.add(text);
        }

        return vec;
    }
}
