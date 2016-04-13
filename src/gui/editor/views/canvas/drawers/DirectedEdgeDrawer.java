package gui.editor.views.canvas.drawers;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Vector;

import models.TikzDirectedEdge;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;
import static constants.GUI.Config.*;

public class DirectedEdgeDrawer extends EdgeDrawer {
    public DirectedEdgeDrawer(){}

    public DrawableTikzComponent toDrawable(TikzDirectedEdge edge){
        DrawableTikzComponent drawablecomponent = super.toDrawable(edge);
        Point start = edge.getFromPosition();
        Point end = edge.getToPosition();
        double eucDist = Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2));
        double alpha = Math.asin(end.getY()/eucDist);
        double beta = alpha - ARROW_ANGLE;
        double gamma = Math.PI/2 - alpha - ARROW_ANGLE;

        // First arrow head segment.
        double firstDeltaX = Math.cos(beta)*ARROW_LENGTH;
        double firstDeltaY = Math.sin(beta)*ARROW_LENGTH;
        Shape firstHeadSegment = new Line2D.Float(end, new Point((int) (end.getX() + firstDeltaX), (int)(end.getY() + firstDeltaY)));
        drawablecomponent.addShape(firstHeadSegment);

        // Second arrow head segment.
        double secondDeltaX = Math.sin(gamma)*ARROW_LENGTH;
        double secondDeltaY = Math.cos(gamma)*ARROW_LENGTH;
        Shape secondHeadSegment = new Line2D.Float(end, new Point((int) (end.getX() + secondDeltaX), (int)(end.getY() + secondDeltaY)));
        drawablecomponent.addShape(secondHeadSegment);
        drawablecomponent.addShape(firstHeadSegment);
        return drawablecomponent;
    }

}
