package gui.editor.views.canvas.drawers;

import java.awt.*;
import java.util.Vector;

import models.TikzComponent;
import constants.Models;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableText;


public abstract class ComponentDrawer implements Drawer {
    public TikzComponent component;

    public Vector<Drawable> toDrawable(){
        Vector<Drawable> vec = new Vector<>();

        if(this.component.getLabel() != Models.DEFAULT.LABEL){
            Drawable text = new DrawableText(
                    this.component.getLabel(),
                    new Point(0, 0)
            );
            vec.add(text);
        }

        return vec;
    }
}
