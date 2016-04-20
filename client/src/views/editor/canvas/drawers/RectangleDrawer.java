package views.editor.canvas.drawers;

import java.awt.*;

import models.tikz.TikzComponent;
import models.tikz.TikzRectangle;
import views.editor.canvas.drawables.DrawableTikzNode;

import javax.swing.*;

public class RectangleDrawer extends NodeDrawer {

    public RectangleDrawer() {
        // this was left intentionally blank
    }

    @Override
    public DrawableTikzNode toDrawable(TikzComponent component, JComponent panel) {
        TikzRectangle rectangle = (TikzRectangle) component;
        DrawableTikzNode drawableComponent = super.toDrawable(rectangle, panel);
        drawableComponent.addShape(getPositionedShape(new Rectangle(rectangle.getWidth(), rectangle.getLength()), rectangle, panel));
        return drawableComponent;

    }
}
