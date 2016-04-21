package views.editor.canvas.drawers;

import javax.swing.*;

import models.tikz.TikzComponent;
import views.editor.canvas.drawables.DrawableTikzComponent;

/**
 * Created by jhellinckx on 13/04/16.
 */
public interface TikzDrawer {
    DrawableTikzComponent toDrawable(TikzComponent component, JComponent panel);
}
