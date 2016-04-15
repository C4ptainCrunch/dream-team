package gui.editor.views.canvas.drawers;

import java.util.Vector;

import gui.editor.views.canvas.drawables.Drawable;

public class UnknownDrawer extends ComponentDrawer {
    public Vector<Drawable> toDrawable() {
        return new Vector<>();
    }
}
