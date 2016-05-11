package views.editor.canvas.drawers;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.*;

import misc.utils.Converter;
import models.tikz.TikzComponent;
import models.tikz.TikzNode;
import models.tikz.TikzPolygon;
import utils.Geom;
import utils.Log;
import views.editor.canvas.drawables.DrawableTikzNode;

public class PolygonDrawer extends NodeDrawer {
    private final static Logger logger = Log.getLogger(PolygonDrawer.class);

    /**
     * Creates a swing drawable polygon object by adding a Polygon shape to the list of shapes of the drawable
     * and correctly setting its position.
     * @param component tikz polygon to draw
     * @param panel panel to draw onto
     * @return drawable with the polygon shape
     */
    @Override
    public DrawableTikzNode toDrawable(TikzComponent component, JComponent panel) {
        TikzPolygon polygon = (TikzPolygon) component;
        DrawableTikzNode drawableComponent = super.toDrawable(polygon, panel);
        drawableComponent.addShape(getPositionedShape(getAwtPolygon(polygon), polygon, panel));
        return drawableComponent;
    }

    /**
     * Creates a awt Polygon from the sides and length of the given TikzPolygon. No responsability on the position.
     * @param polygon the polygon tikz object to create an awt polygon from.
     * @return the awt polygon
     */
    private Polygon getAwtPolygon(TikzPolygon polygon) {
        int vertices = polygon.getSides();
        float size = polygon.getLength();

        return new Polygon(getPolygonXCoords(vertices, (int)Converter.centimetersToPixels(size)), getPolygonYCoords(vertices, (int)Converter.centimetersToPixels(size)), vertices);
    }

    /**
     * Uses polar coordinates to get an array of vertices x coordinates
     * @param vertices number of polygon vertices
     * @param size length of each side
     * @return array of the vertices x coordinates
     */
    private int[] getPolygonXCoords(int vertices, int size){
        double step = 2 * Math.PI / vertices;
        double offset = (Math.PI - step) / 2;
        int[] x = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            x[i] = size + (int) (Math.cos(i * step + offset) * size);
        }
        return x;
    }

    /**
     * Uses polar coordinates to get an array of vertices y coordinates
     * @param vertices number of polygon vertices
     * @param size length of each side
     * @return array of the vertices y coordinates
     */
    private int[] getPolygonYCoords(int vertices, int size){
        double step = 2 * Math.PI / vertices;
        double offset = (Math.PI - step) / 2;
        int[] y = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            y[i] = size + (int) (Math.sin(i * step + offset) * size);
        }
        return y;
    }

    /**
     * Set the position of the given polygon. uses swing coordinates. The polygon
     * is centered and translated by the node coordinates converted to swing coordinates
     * @param shape polygon to position. Type is Shape in order to override parent method.
     * @param node node corresponding to the shape
     * @param panel panel to draw the shape onto
     * @return the positioned polygon
     */
    @Override
    public Polygon getPositionedShape(Shape shape, TikzNode node, JComponent panel){
        Polygon polygon = (Polygon) shape;
        Rectangle bounds = polygon.getBounds();
        polygon.translate((int)-bounds.getWidth() / 2,(int) -bounds.getHeight() /2);
        Point2D.Float swingPosition = Converter.tikz2swing(node.getPosition(), panel);
        polygon.translate((int)swingPosition.getX(), (int)swingPosition.getY());
        return polygon;
    }

    /**
     * Determines the anchors of the given polygon.
     * Constructs the Polygon shape as if we wanted to make a Drawable.
     * Then, uses the obtained shape to get the vertices and compute
     * the center of each following pair of vertices for anchors.
     * @param node node to get the anchors from
     * @param panel panel on which the node is drawn
     * @return a list of swing anchors
     */
    @Override
    public List<Point2D.Float> getAnchors(TikzNode node, JComponent panel){
        TikzPolygon polygon = (TikzPolygon) node;
        Polygon positionedPolygon = getPositionedShape(getAwtPolygon(polygon), polygon, panel);
        int[] x = positionedPolygon.xpoints;
        int[] y = positionedPolygon.ypoints;
        int vertices = positionedPolygon.npoints;
        List<Point2D.Float> anchors = new ArrayList<>();
        int next_i; Point2D.Float firstVertex; Point2D.Float secondVertex;
        for(int i = 0; i < vertices; ++i){
            next_i = (i + 1) % vertices;
            firstVertex = new Point2D.Float(x[i], y[i]);
            secondVertex = new Point2D.Float(x[next_i], y[next_i]);
            anchors.add(Geom.middle(firstVertex, secondVertex));
        }
        return anchors;
    }
}
