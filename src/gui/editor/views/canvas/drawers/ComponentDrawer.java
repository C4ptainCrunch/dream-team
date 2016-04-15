package gui.editor.views.canvas.drawers;

import java.awt.*;

import models.TikzComponent;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;

public abstract class ComponentDrawer implements TikzDrawer {

    public DrawableTikzComponent toDrawable(TikzComponent component) {
        DrawableTikzComponent drawableComponent = new DrawableTikzComponent(component);
        drawableComponent.setStroke(new BasicStroke(component.getStroke()));
        drawableComponent.setColor(component.getColor());
        return drawableComponent;
    }
}
