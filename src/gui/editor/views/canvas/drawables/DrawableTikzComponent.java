package gui.editor.views.canvas.drawables;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.*;

import models.tikz.TikzComponent;
import models.tikz.TikzEdge;
import models.tikz.TikzNode;
import constants.Models;

public class DrawableTikzComponent implements Drawable {
    private ArrayList<Shape> shapes;
    private Stroke stroke;
    private Color color;
    private Color background;
    private TikzComponent component;

    public DrawableTikzComponent(TikzComponent component) {
        this.component = component;
        shapes = new ArrayList<>();
    }

    public TikzComponent getComponent() {
        return component;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public Stroke getStroke() {
        return this.stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor(Color color) {
        return this.color;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getBackground(Color background) {
        return this.background;
    }

    public void draw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Stroke old_stroke = g.getStroke();
        Color old_color = g.getColor();

        if (component.getLabel() != Models.DEFAULT.LABEL) {
            g.drawString(this.component.getLabel(), 0, 0);
        }

        g.setStroke(this.stroke);

        g.setColor(this.background);

        for (Shape shape : shapes) {
            g.fill(shape);
        }
        g.setColor(this.color);
        for (Shape shape : shapes) {
            g.draw(shape);
        }

        g.setColor(old_color);
        g.setStroke(old_stroke);
    }

    public void translate(Point translation) {
        AffineTransform transform = new AffineTransform(1, 0, 0, 1, translation.getX(), translation.getY());
        for (int i = 0; i < shapes.size(); ++i) {
            shapes.set(i, transform.createTransformedShape(shapes.get(i)));
        }
    }

    public void center() {
        for (int i = 0; i < shapes.size(); ++i) {
            Rectangle bounds = shapes.get(i).getBounds();
            AffineTransform transform = new AffineTransform(1, 0, 0, 1, -bounds.width / 2, -bounds.height / 2);
            shapes.set(i, transform.createTransformedShape(shapes.get(i)));
        }
    }

    public void tikz2swing(JPanel container) {
        Point tikz_position = new Point(0, 0);
        if (component instanceof TikzNode) {
            center();
            tikz_position.setLocation(((TikzNode) component).getPosition());
            AffineTransform transform = new AffineTransform(1, 0, 0, 1,
                    tikz_position.getX() + (container.getWidth() / 2),
                    tikz_position.getY() + (container.getHeight() / 2));
            for (int i = 0; i < shapes.size(); ++i) {
                shapes.set(i, transform.createTransformedShape(shapes.get(i)));
            }
        } else if (component instanceof TikzEdge) {
            tikz_position.setLocation(((TikzEdge) component).getPosition());
            tikz_position.setLocation(-tikz_position.getX(), -tikz_position.getY());
            AffineTransform transform = new AffineTransform(1, 0, 0, 1,
                    tikz_position.getX() + (container.getWidth() / 2),
                    tikz_position.getY() + (container.getHeight() / 2));
            for (int i = 0; i < shapes.size(); ++i) {
                shapes.set(i, transform.createTransformedShape(shapes.get(i)));
            }
        }
    }

    public boolean contains(Point point) {
        boolean contain = false;
        for (int i = 0; !contain && i < shapes.size(); i++) {
            contain = shapes.get(i).contains(point);
        }
        return contain;
    }
}
