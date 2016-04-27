package views.editor.canvas.drawers;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.*;

import models.tikz.TikzComponent;
import models.tikz.TikzNode;
import models.tikz.TikzPolygon;
import utils.Log;
import views.editor.canvas.drawables.DrawableTikzNode;

public class PolygonDrawer extends NodeDrawer {
    private final static Logger logger = Log.getLogger(PolygonDrawer.class);

    public PolygonDrawer() {
        // this was left intentionally blank
    }

    @Override
    public DrawableTikzNode toDrawable(TikzComponent component, JComponent panel) {
        TikzPolygon polygon = (TikzPolygon) component;
        DrawableTikzNode drawableComponent = super.toDrawable(polygon, panel);
        drawableComponent.addShape(getPositionedShape(getAwtPolygon(polygon), polygon, panel));
        return drawableComponent;
    }

    // Source : http://stackoverflow.com/a/29546432
    private Polygon getAwtPolygon(TikzPolygon polygon) {
        int vertices = polygon.getSides();
        int size = polygon.getLength();

        return new Polygon(getPolygonXCoords(vertices, size), getPolygonYCoords(vertices, size), vertices);
    }

    private int[] getPolygonXCoords(int vertices, int size){
        double step = 2 * Math.PI / vertices;
        double offset = (Math.PI - step) / 2;
        int[] x = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            x[i] = size + (int) (Math.cos(i * step + offset) * size);
        }
        return x;
    }

    private int[] getPolygonYCoords(int vertices, int size){
        double step = 2 * Math.PI / vertices;
        double offset = (Math.PI - step) / 2;
        int[] y = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            y[i] = size + (int) (Math.sin(i * step + offset) * size);
        }
        return y;
    }


    @Override
    public List<Point> getAnchors(TikzNode node){
        TikzPolygon polygon = (TikzPolygon) node;
        Polygon positionedPolygon = getPositionedShape(getAwtPolygon(polygon), polygon);
        int[] x = positionedPolygon.xpoints;
        int[] y = positionedPolygon.ypoints;
        List<Point> anchors = new ArrayList<>();
        for(int i = 0; i < positionedPolygon.npoints; ++i){
            anchors.add(new Point(x[i], y[i]));
        }
        return anchors;
    }
}
