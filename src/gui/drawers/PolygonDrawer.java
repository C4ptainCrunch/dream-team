package gui.drawers;

import gui.drawables.Drawable;
import gui.drawables.DrawableShape;
import gui.drawables.DrawableText;
import models.TikzPolygon;
import constants.Models;

import java.awt.*;
import java.util.Vector;

public class PolygonDrawer implements Drawer {
    private TikzPolygon tikzPolygon;

    public PolygonDrawer(TikzPolygon tikzPolygon) {
        this.tikzPolygon = tikzPolygon;
    }

    public Vector<Drawable> toDrawable(){
        Vector<Drawable> vec = new Vector<>();

        Drawable shape = new DrawableShape(
                getAwtPolygon(),
                new BasicStroke(2),
                new Color(0, 0, 0)
        );

        vec.add(shape);

        if(this.tikzPolygon.getLabel() != Models.DEFAULT.LABEL){
            Drawable text = new DrawableText(
                    this.tikzPolygon.getLabel(),
                    new Point(15, 20)
            );
            vec.add(text);
        }
        return vec;
    }

    // Source : http://stackoverflow.com/a/29546432
    private Polygon getAwtPolygon() {
        int vertices = this.tikzPolygon.getSides();
        int size = this.tikzPolygon.getLength();

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
