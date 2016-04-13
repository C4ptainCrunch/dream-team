package gui.editor.views.canvas.drawers;

import java.awt.*;
import java.util.Vector;

import models.TikzRectangle;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;

public class RectangleDrawer extends ComponentDrawer {

    public RectangleDrawer() {}

    public DrawableTikzComponent toDrawable(TikzRectangle rectangle){
        DrawableTikzComponent drawableComponent = super.toDrawable(rectangle);
        drawableComponent.addShape( new Rectangle(rectangle.getWidth(), rectangle.getLength()));
        drawableComponent.setStroke(new BasicStroke(2));
        drawableComponent.setColor(rectangle.getColor());
        drawableComponent.setBackground(rectangle.getBackground());
        return drawableComponent;

    }
}
