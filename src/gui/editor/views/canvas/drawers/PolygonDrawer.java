package gui.editor.views.canvas.drawers;

import java.awt.*;
import java.util.Vector;

import models.TikzPolygon;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableShape;

public class PolygonDrawer extends ComponentDrawer{
    public PolygonDrawer(TikzPolygon component) {
        this.component = component;
    }

    public TikzPolygon getComponent(){
        return (TikzPolygon) this.component;
    }

    public Vector<Drawable> toDrawable(){
        Vector<Drawable> vec = super.toDrawable();

        Drawable shape = new DrawableShape(
                getAwtPolygon(),
                new BasicStroke(2),
                getComponent().getColor(),
                getComponent().getBackground(),
                true
        );
        vec.add(shape);
        return vec;
    }

    // Source : http://stackoverflow.com/a/29546432
    private Polygon getAwtPolygon() {
        int vertices = getComponent().getSides();
        int size = getComponent().getLength();

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
