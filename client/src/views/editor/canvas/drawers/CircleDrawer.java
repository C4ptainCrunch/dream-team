package views.editor.canvas.drawers;

import java.awt.geom.Ellipse2D;

import models.tikz.TikzCircle;
import models.tikz.TikzComponent;
import views.editor.canvas.drawables.DrawableTikzComponent;

import java.awt.geom.Ellipse2D;

import models.tikz.TikzCircle;
import models.tikz.TikzComponent;
import views.editor.canvas.drawables.DrawableTikzNode;

public class CircleDrawer extends NodeDrawer {

    public CircleDrawer() {
        // this was left intentionally blank
    }

    @Override
    public DrawableTikzNode toDrawable(TikzComponent component) {
        TikzCircle circle = (TikzCircle) component;
        DrawableTikzNode drawableComponent = super.toDrawable(circle);

        float size = circle.getRadius() * 2;
        drawableComponent.addShape(new Ellipse2D.Float(0, 0, size, size));
        return drawableComponent;
    }
}
