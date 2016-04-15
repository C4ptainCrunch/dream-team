package gui.editor.views.canvas.drawers;

import gui.editor.views.canvas.drawables.DrawableTikzComponent;
import models.TikzComponent;

/**
 * Created by jhellinckx on 13/04/16.
 */
public interface TikzDrawer {
    DrawableTikzComponent toDrawable(TikzComponent component);
}
