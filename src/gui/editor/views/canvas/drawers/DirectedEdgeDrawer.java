package gui.editor.views.canvas.drawers;

import static constants.GUI.Config.ARROW_ANGLE;
import static constants.GUI.Config.ARROW_LENGTH;

import java.awt.*;
import java.awt.geom.Line2D;

import models.tikz.TikzComponent;
import models.tikz.TikzDirectedEdge;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;

public class DirectedEdgeDrawer extends EdgeDrawer {
    public DirectedEdgeDrawer() {
    }

    @Override
    public DrawableTikzComponent toDrawable(TikzComponent component) {
        TikzDirectedEdge edge = (TikzDirectedEdge) component;
        DrawableTikzComponent drawablecomponent = super.toDrawable(edge);
        Point start = edge.getFromPosition();
        Point end = edge.getToPosition();
        double eucDist = Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2));
        double alpha = Math.asin(end.getY() / eucDist);
        double beta = alpha - ARROW_ANGLE;
        double gamma = Math.PI / 2 - alpha - ARROW_ANGLE;

        int corrX, corrY;
        corrX = (end.getX() > start.getX()) ? -1 : 1;
        corrY = (end.getY() > start.getY()) ? -1 : 1;

        // First arrow head segment.
        double firstDeltaX = Math.cos(beta) * ARROW_LENGTH;
        double firstDeltaY = Math.sin(beta) * ARROW_LENGTH;
        Shape firstHeadSegment = new Line2D.Float(end,
                new Point((int) (end.getX() + firstDeltaX * corrX), (int) (end.getY() + firstDeltaY * corrY)));

        // Second arrow head segment.
        double secondDeltaX = Math.sin(gamma) * ARROW_LENGTH;
        double secondDeltaY = Math.cos(gamma) * ARROW_LENGTH;
        Shape secondHeadSegment = new Line2D.Float(end,
                new Point((int) (end.getX() + secondDeltaX * corrX), (int) (end.getY() + secondDeltaY * corrY)));

        drawablecomponent.addShape(firstHeadSegment);
        drawablecomponent.addShape(secondHeadSegment);
        return drawablecomponent;
    }

}
