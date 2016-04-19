package gui.editor.views.canvas.drawers;

import java.awt.*;
import java.util.logging.Logger;

import models.tikz.TikzComponent;
import models.tikz.TikzPolygon;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;
import utils.Log;

public class PolygonDrawer extends ComponentDrawer {
    private final static Logger logger = Log.getLogger(PolygonDrawer.class);

    public PolygonDrawer() {
        // this was left intentionally blank
    }

    @Override
    public DrawableTikzComponent toDrawable(TikzComponent component) {
        TikzPolygon polygon = (TikzPolygon) component;
        DrawableTikzComponent drawableComponent = super.toDrawable(polygon);
        drawableComponent.addShape(getAwtPolygon(polygon));
        drawableComponent.setBackground(polygon.getBackgroundColor());
        return drawableComponent;
    }

    // Source : http://stackoverflow.com/a/29546432
    private Polygon getAwtPolygon(TikzPolygon polygon) {
        int vertices = polygon.getSides();
        int size = polygon.getLength();

        double step = 2 * Math.PI / vertices;
        double offset = (Math.PI - step) / 2;
        int[] x = new int[vertices];
        int[] y = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            x[i] = size + (int) (Math.cos(i * step + offset) * size);
            y[i] = size + (int) (Math.sin(i * step + offset) * size);
        }

        return new Polygon(x, y, vertices);
    }
}
