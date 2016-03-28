package gui.editor.views.canvas.drawers;

import gui.editor.views.canvas.drawables.Drawable;

import java.util.Vector;

public class UnknownDrawer extends ComponentDrawer{
    public Vector<Drawable> toDrawable(){
        return new Vector<>();
    }
}
