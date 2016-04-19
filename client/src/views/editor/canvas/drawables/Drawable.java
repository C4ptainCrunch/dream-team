package views.editor.canvas.drawables;

import java.awt.*;

import javax.swing.*;

public interface Drawable {
    void draw(Graphics2D g, JComponent panel);
    void translate(Point translation);
}
