package views.editor.canvas.drawers;

import java.util.List;
import java.util.Vector;

import views.editor.canvas.drawables.Drawable;

public class UnknownDrawer extends ComponentDrawer {
    public List<Drawable> toDrawable() {
        return new Vector<>();
    }
}
