package gui.editor.views.canvas.drawables;

import java.awt.*;

public interface Drawable {
    void draw(Graphics2D g);
    void translate(Point translation);
}
