package views.editor.canvas.drawables;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import models.tikz.TikzComponent;

public abstract class DrawableTikzComponent implements Drawable {
    private final java.util.List<Shape> shapes = new ArrayList<Shape>();
    private final java.util.List<Shape> strokes = new ArrayList<>();
    private final TikzComponent component;

    public DrawableTikzComponent(TikzComponent component) {
        this.component = component;
    }

    public TikzComponent getComponent() {
        return component;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        strokes.add(new BasicStroke(this.component.getStroke()).createStrokedShape(shape));
    }


    // Useful for drawing the Label.
    public Rectangle2D getBounds() {
        if (!shapes.isEmpty()) {
            Rectangle2D bounds = shapes.get(0).getBounds2D();
            for (int i = 1; i < shapes.size(); ++i) {
                bounds.add(shapes.get(i).getBounds2D());
            }
            return bounds;
        } else{
            return new Rectangle2D.Double();
        }
    }

    public java.util.List<Shape> getShapes() {
        return shapes;
    }

    public java.util.List<Shape> getStrokes() { return strokes; }

    public boolean contains(Point2D.Float point) {
        for (Shape shape : shapes) {
            if (shape.contains(point)) {
                return true;
            }
        }
        for(Shape stroke : strokes){
            if(stroke.contains(point)){
                return true;
            }
        }
        return false;
    }

    public boolean intersects(Shape s){
        for (Shape shape : shapes){
            if (shape.intersects(s.getBounds2D())){
                return true;
            }
        }
        for (Shape stroke: strokes){
            if (stroke.intersects(s.getBounds2D())){
                return true;
            }
        }
        return false;
    }

    public boolean hasAsComponent(TikzComponent comp) {
        return component.equals(comp);
    }
}
