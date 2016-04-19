package views.editor.canvas.drawers;

import java.awt.*;

import models.tikz.TikzComponent;
import models.tikz.TikzRectangle;
import views.editor.canvas.drawables.DrawableTikzNode;

public class RectangleDrawer extends NodeDrawer {

    public RectangleDrawer() {
        // this was left intentionally blank
    }

    @Override
    public DrawableTikzNode toDrawable(TikzComponent component) {
        TikzRectangle rectangle = (TikzRectangle) component;
        DrawableTikzNode drawableComponent = super.toDrawable(rectangle);
        drawableComponent.addShape(new Rectangle(rectangle.getWidth(), rectangle.getLength()));
        return drawableComponent;

    }
}
