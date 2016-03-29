package gui.editor.views.canvas.drawers;

import java.util.Vector;

import gui.editor.views.canvas.drawables.Drawable;

public interface Drawer {
    Vector<Drawable> toDrawable();
}
