package views.editor.canvas.drawables;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import models.tikz.TikzComponent;
import views.editor.canvas.drawables.Drawable;

public abstract class DrawableTikzComponent implements Drawable {
    private final java.util.List<Shape> shapes = new ArrayList<Shape>();
    private final TikzComponent component;

    public DrawableTikzComponent(TikzComponent component) {
        this.component = component;
    }

    public TikzComponent getComponent() {
        return component;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }


    public java.util.List<Shape> getShapes() { return shapes; }

    public boolean contains(Point point) {
        for(Shape shape : shapes){
            if(shape.contains(point)){
                return true;
            }
        }
        return false;
    }

    public boolean hasAsComponent(TikzComponent comp){
        return component.equals(comp);
    }
}
