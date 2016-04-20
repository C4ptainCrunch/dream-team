package views.editor.canvas.drawables;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
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

    // Useful for drawing the Label.
    public Rectangle2D getBounds(){
        if(!shapes.isEmpty()){
            Rectangle2D bounds = shapes.get(0).getBounds2D();
            for(int i = 1; i < shapes.size(); ++i){
                bounds.add(shapes.get(i).getBounds2D());
            }
            return bounds;
        }
        else return new Rectangle2D.Double();
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
