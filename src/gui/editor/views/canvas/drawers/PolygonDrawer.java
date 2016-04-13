package gui.editor.views.canvas.drawers;

import java.awt.*;
import java.util.Vector;

import models.TikzComponent;
import models.TikzPolygon;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;

public class PolygonDrawer extends ComponentDrawer{
    public PolygonDrawer(){}

    @Override
    public DrawableTikzComponent toDrawable(TikzComponent component){
        TikzPolygon polygon = (TikzPolygon)component;
        DrawableTikzComponent drawableComponent = super.toDrawable(polygon);
        drawableComponent.addShape(getAwtPolygon(polygon));
        drawableComponent.setStroke(new BasicStroke(2));
        drawableComponent.setColor(polygon.getColor());
        drawableComponent.setBackground(polygon.getBackground());
        return drawableComponent;
    }

    // Source : http://stackoverflow.com/a/29546432
    private Polygon getAwtPolygon(TikzPolygon polygon) {
        int vertices = polygon.getSides();
        int size = polygon.getLength();

        double step = 2 * Math.PI / vertices;
        int[] x = new int[vertices];
        int[] y = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            x[i] = size + (int) (Math.cos(i * step) * size);
            y[i] = size + (int) (Math.sin(i * step) * size);
        }
        Polygon p = new Polygon(x, y, vertices);
        return p;
    }
}
