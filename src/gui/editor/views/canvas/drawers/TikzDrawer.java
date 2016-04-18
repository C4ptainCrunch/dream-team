package gui.editor.views.canvas.drawers;

import models.tikz.TikzComponent;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;

/**
 * Created by jhellinckx on 13/04/16.
 */
public interface TikzDrawer {
    DrawableTikzComponent toDrawable(TikzComponent component);
}
