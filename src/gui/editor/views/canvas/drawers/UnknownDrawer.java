package gui.editor.views.canvas.drawers;

import java.util.List;
import java.util.Vector;

import gui.editor.views.canvas.drawables.Drawable;

public class UnknownDrawer extends ComponentDrawer {
    public List<Drawable> toDrawable() {
        return new Vector<>();
    }
}
