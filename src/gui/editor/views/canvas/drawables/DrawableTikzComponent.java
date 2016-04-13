package gui.editor.views.canvas.drawables;
import constants.Models;
import models.TikzComponent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;
import javax.swing.JPanel;

public class DrawableTikzComponent implements Drawable{
    private ArrayList<Shape> shapes;
    private Stroke stroke;
    private Color color;
    private Color background;
    private TikzComponent component;

    public DrawableTikzComponent(TikzComponent component) { this.component = component;}

    public TikzComponent getComponent() { return component; }

    public void addShape(Shape shape) { shapes.add(shape); }
    public ArrayList<Shape> getShapes() { return shapes; }

    public void setStroke(Stroke stroke) { this.stroke = stroke; }
    public Stroke getStroke(Stroke stroke) { return this.stroke; }

    public void setColor(Color color) { this.color = color; }
    public Color getColor(Color color) { return this.color; }

    public void setBackground(Color background) { this.background = background; }
    public Color getBackground(Color background) { return this.background; }

    public void draw(Graphics2D g){
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Stroke old_stroke = g.getStroke();
        Color old_color = g.getColor();

        if(component.getLabel() != Models.DEFAULT.LABEL) {
            g.drawString(this.component.getLabel(), 0, 0);
        }

        g.setStroke(this.stroke);

        g.setColor(this.background);

        for(Shape shape : shapes){
            g.fill(shape);
        }
        g.setColor(this.color);
        for(Shape shape : shapes){
            g.draw(shape);
        }

        g.setColor(old_color);
        g.setStroke(old_stroke);
    }

    public void translate(Point translation){
        AffineTransform transform = new AffineTransform(
                1, 0, 0, 1, translation.getX(), translation.getY()
        );
        for(int i = 0; i < shapes.size(); ++i){
            shapes.set(i, transform.createTransformedShape(shapes.get(i)));
        }
    }

    public void tikz2swing(Point tikz_position, JPanel container){
        AffineTransform transform = new AffineTransform(
                1, 0, 0, 1, tikz_position.getX()+(container.getWidth()/2), tikz_position.getY()+(container.getHeight()/2)
        );
        for(int i = 0; i < shapes.size(); ++i){
            shapes.set(i, transform.createTransformedShape(shapes.get(i)));
        }
    }
}
