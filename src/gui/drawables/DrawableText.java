package gui.drawables;
import java.awt.*;

public class DrawableText implements Drawable{
    private Point position;
    private String text;

    public DrawableText(String text, Point position){
        this.text = text;
        this.position = position;
    }

    public void draw(Graphics2D g){
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g.drawString(this.text, (float) this.position.getX(), (float) this.position.getY());
    }

    public void translate(Point translation){
        this.position.translate((int) translation.getX(), (int) translation.getY());
    }
}
