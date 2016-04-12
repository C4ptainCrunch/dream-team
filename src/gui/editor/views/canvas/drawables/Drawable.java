package gui.editor.views.canvas.drawables;

import javax.swing.JPanel;
import java.awt.*;

public interface Drawable {
    void draw(Graphics2D g);
    void translate(Point translation);
    void translate(Point tikz_position, JPanel container);
}
