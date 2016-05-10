package views.editor.canvas.drawers;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.*;

import misc.utils.Converter;
import models.tikz.TikzComponent;
import models.tikz.TikzEdge;
import views.editor.canvas.drawables.DrawableTikzEdge;
import constants.Models;

public abstract class EdgeDrawer extends ComponentDrawer {

    /**
     * Creates the drawable swing object for the given edge. By default it is considered undirected.
     * @param component the edge to create a drawable from
     * @param panel the panl on which the edge needs to be drawn
     * @return the drawable tikz edge
     */
    @Override
    public DrawableTikzEdge toDrawable(TikzComponent component, JComponent panel) {
        TikzEdge edge = (TikzEdge) component;
        DrawableTikzEdge drawableComponent = new DrawableTikzEdge(component);
        drawableComponent
                .addShape(new Line2D.Float(fromPosition(edge, panel), toPosition(edge, panel)));
        return drawableComponent;
    }

    /**
     * Given the edge, determine its first (from) point. The determined point
     * is a swing point.
     * If the edge has no from or to node, set the position as the center of the panel.
     * Else, get the closest from node anchor to the to node.
     * @param edge the edge to determine the from position
     * @param panel the panel on which the edge is drawn
     * @return the swing from point of the edge
     */
    public Point2D.Float fromPosition(TikzEdge edge, JComponent panel) {
        Point2D.Float start;
        if (edge.getFirstNode() == null || edge.getSecondNode() == null) {
            start = Converter.tikz2swing(new Point2D.Float(-Models.DEFAULT.EDGE_X_LENGTH / 2, 0), panel);
        } else {
            // Get closest anchor
            start = Drawer.closestAnchor(edge.getFirstNode(), edge.getToPosition(), panel);
        }
        return start;
    }

    /**
     * Given the edge, determine its second (to) point. The determined point
     * is a swing point.
     * If the edge has no from or to node, set the position as the center of the panel.
     * Else, get the closest to node anchor to the from node.
     * @param edge the edge to determine the to position
     * @param panel the panel on which the edge is drawn
     * @return the swing to point of the edge
     */
    public Point2D.Float toPosition(TikzEdge edge, JComponent panel) {
        Point2D.Float end;
        if (edge.getFirstNode() == null || edge.getSecondNode() == null) {
            end = Converter.tikz2swing(new Point2D.Float(Models.DEFAULT.EDGE_X_LENGTH / 2, 0), panel);
        } else {
            end = Drawer.closestAnchor(edge.getSecondNode(), edge.getFromPosition(), panel);
        }
        return end;
    }
}
