package gui.drawables;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawableShape implements Drawable{
    private Shape shape;

    public DrawableShape(Shape shape){
        this.shape = shape;
    }

    public void draw(Graphics2D g){
        g.draw(this.shape);
    }

    public void translate(Point translation){
        AffineTransform transform = new AffineTransform(
                0, 0, 0, 0, translation.getX(), translation.getY()
        );
        this.shape = transform.createTransformedShape(this.shape);
    }
}
