package views.editor.canvas.drawers;

import javax.swing.*;

import models.tikz.TikzComponent;
import views.editor.canvas.drawables.DrawableTikzComponent;

import java.awt.*;

public interface TikzDrawer {
    DrawableTikzComponent toDrawable(TikzComponent component, JComponent panel);
}
