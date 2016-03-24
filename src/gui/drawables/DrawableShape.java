package gui.drawables;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawableShape implements Drawable{
    private Shape shape;
    private Stroke stroke;
    private Color color;

    public DrawableShape(Shape shape, Stroke stroke, Color color){
        this.shape = shape;
        this.stroke = stroke;
        this.color = color;
    }

    public DrawableShape(Shape shape, Stroke stroke, Color color, Boolean center){
        this(shape, stroke, color);
        if (center) {
            Rectangle bounds = this.shape.getBounds();
            this.translate(new Point(-bounds.width / 2, -bounds.height / 2));
        }
    }

    public void draw(Graphics2D g){
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Stroke old_stroke = g.getStroke();
        Color old_color = g.getColor();

        g.setStroke(this.stroke);

        g.setColor(Color.cyan);
        g.fill(this.shape);

        g.setColor(this.color);
        g.draw(this.shape);

        g.setColor(old_color);
        g.setStroke(old_stroke);
    }

    public void translate(Point translation){
        AffineTransform transform = new AffineTransform(
                1, 0, 0, 1, translation.getX(), translation.getY()
        );
        this.shape = transform.createTransformedShape(this.shape);
    }
}
