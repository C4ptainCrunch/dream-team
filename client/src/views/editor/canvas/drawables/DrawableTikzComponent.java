package views.editor.canvas.drawables;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import models.tikz.TikzComponent;
import views.editor.canvas.drawables.Drawable;

public abstract class DrawableTikzComponent implements Drawable {
    private final Area area = new Area();
    private Area drawnArea;
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

    protected void setDrawing(Area area) {
        this.drawnArea = area;
    }

    public void translate(Point translation) {
        AffineTransform affineTransform = new AffineTransform(1, 0, 0, 1, translation.getX(), translation.getY());
        drawnArea.transform(affineTransform);
    }

    public boolean contains(Point point) {
        return drawnArea.contains(point);
    }

    public boolean hasAsComponent(TikzComponent comp){
        return component.equals(comp);
    }
}
