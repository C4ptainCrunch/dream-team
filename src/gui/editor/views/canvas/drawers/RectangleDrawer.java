package gui.editor.views.canvas.drawers;

import java.awt.*;

import models.tikz.TikzComponent;
import models.tikz.TikzRectangle;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;

public class RectangleDrawer extends ComponentDrawer {

    public RectangleDrawer() {
    }

    @Override
    public DrawableTikzComponent toDrawable(TikzComponent component) {
        TikzRectangle rectangle = (TikzRectangle) component;
        DrawableTikzComponent drawableComponent = super.toDrawable(rectangle);
        drawableComponent.addShape(new Rectangle(rectangle.getWidth(), rectangle.getLength()));
        drawableComponent.setBackground(rectangle.getBackground());
        return drawableComponent;

    }
}
