package views.editor.canvas.drawers;

import static constants.GUI.Config.ARROW_ANGLE;
import static constants.GUI.Config.ARROW_LENGTH;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.*;

import misc.utils.Converter;
import models.tikz.TikzComponent;
import models.tikz.TikzDirectedEdge;
import views.editor.canvas.drawables.DrawableTikzEdge;

public class DirectedEdgeDrawer extends EdgeDrawer {
    /**
     * Creates the drawable swing object for the given directed edge.
     * First get the core line (with no arrow head) of the edge and add the line shape to the
     * drawable. Then compute the arrow head by using polar coordinates.
     * @param component the directed edge to create a drawable from
     * @param panel the panel on which the edge needs to be drawn
     * @return the drawable tikz directed edge
     */
    @Override
    public DrawableTikzEdge toDrawable(TikzComponent component, JComponent panel) {
        TikzDirectedEdge edge = (TikzDirectedEdge) component;
        DrawableTikzEdge drawablecomponent = super.toDrawable(edge, panel);
        Point2D.Float start = Converter.swing2tikz(fromPosition(edge, panel), panel);
        Point2D.Float end = Converter.swing2tikz(toPosition(edge, panel), panel);
        double dy = end.y - start.y;
        double dx = end.x - start.x;
        double theta = Math.atan2(dy, dx);
        double x, y, rho = theta + ARROW_ANGLE;
        x = end.x - ARROW_LENGTH * Math.cos(rho);
        y = end.y - ARROW_LENGTH * Math.sin(rho);
        Point2D.Float firstHeadPoint = new Point2D.Float((float) x, (float) y);
        rho = theta - ARROW_ANGLE;
        x = end.x - ARROW_LENGTH * Math.cos(rho);
        y = end.y - ARROW_LENGTH * Math.sin(rho);
        Point2D.Float secondHeadPoint = new Point2D.Float((float) x, (float) y);
        Shape secondHeadSegment = new Line2D.Float(Converter.tikz2swing(end, panel), Converter.tikz2swing(secondHeadPoint, panel));
        Shape firstHeadSegment = new Line2D.Float(Converter.tikz2swing(end, panel), Converter.tikz2swing(firstHeadPoint, panel));
        drawablecomponent.addShape(firstHeadSegment);
        drawablecomponent.addShape(secondHeadSegment);
        return drawablecomponent;
    }

}
