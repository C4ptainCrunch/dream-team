package gui.editor.views.canvas.drawers;

import java.awt.*;
import java.util.Vector;

import models.TikzComponent;
import models.TikzRectangle;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;

public class RectangleDrawer extends ComponentDrawer {

    public RectangleDrawer() {}

    @Override
    public DrawableTikzComponent toDrawable(TikzComponent component){
        TikzRectangle rectangle = (TikzRectangle)component;
        DrawableTikzComponent drawableComponent = super.toDrawable(rectangle);
        drawableComponent.addShape( new Rectangle(rectangle.getWidth(), rectangle.getLength()));
        drawableComponent.setStroke(new BasicStroke(rectangle.getStroke()));
        drawableComponent.setColor(rectangle.getColor());
        drawableComponent.setBackground(rectangle.getBackground());
        return drawableComponent;

    }
}
