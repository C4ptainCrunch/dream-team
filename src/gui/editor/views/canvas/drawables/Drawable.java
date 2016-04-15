package gui.editor.views.canvas.drawables;

import java.awt.*;

import javax.swing.*;

public interface Drawable {
    void draw(Graphics2D g);
    void translate(Point translation);
    void tikz2swing(JPanel container);
}
