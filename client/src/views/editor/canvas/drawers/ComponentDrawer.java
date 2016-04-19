package views.editor.canvas.drawers;

import java.awt.*;

import models.tikz.TikzComponent;
import views.editor.canvas.drawables.DrawableTikzComponent;

public abstract class ComponentDrawer implements TikzDrawer {

    public DrawableTikzComponent toDrawable(TikzComponent component) {
        DrawableTikzComponent drawableComponent = new DrawableTikzComponent(component);
        drawableComponent.setStroke(new BasicStroke(component.getStroke()));
        drawableComponent.setColor(component.getColor());
        return drawableComponent;
    }
}
