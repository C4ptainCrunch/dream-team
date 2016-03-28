package gui.editor.views.canvas.drawers;

import gui.editor.views.canvas.drawables.Drawable;
import java.util.Vector;

public interface Drawer {
    Vector<Drawable> toDrawable();
}
