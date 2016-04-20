package views.editor.canvas.drawables;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import models.tikz.TikzComponent;
import views.editor.canvas.drawables.Drawable;

public abstract class DrawableTikzComponent implements Drawable {
    private final Area area = new Area();
    private final TikzComponent component;

    public DrawableTikzComponent(TikzComponent component) {
        this.component = component;
    }

    public TikzComponent getComponent() {
        return component;
    }

    public void addShape(Shape shape) {
        area.add(new Area(shape));
    }

    public Area getArea() { return area; }

    public boolean contains(Point point) {
        return area.contains(point);
    }

    public boolean hasAsComponent(TikzComponent comp){
        return component.equals(comp);
    }
}
