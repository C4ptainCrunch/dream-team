package views.editor.canvas.drawables;

import java.awt.*;

import models.tikz.TikzComponent;
import models.tikz.TikzEdge;

public class DrawableTikzEdge extends DrawableTikzComponent {

    public DrawableTikzEdge(TikzComponent component) {
        super(component);
    }

    @Override
    public TikzEdge getComponent() {
        return (TikzEdge) super.getComponent();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Stroke old_stroke = g.getStroke();
        Color old_color = g.getColor();

        TikzEdge component = getComponent();

        g.setStroke(new BasicStroke(component.getStroke()));
        g.setColor(component.getColor());
        for (Shape shape : getShapes()) {
            g.fill(shape);
            g.draw(shape);
        }

        g.setColor(old_color);
        g.setStroke(old_stroke);
    }
}
