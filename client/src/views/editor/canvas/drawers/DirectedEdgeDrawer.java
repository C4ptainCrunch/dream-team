package views.editor.canvas.drawers;

import static constants.GUI.Config.ARROW_ANGLE;
import static constants.GUI.Config.ARROW_LENGTH;

import java.awt.*;
import java.awt.geom.Line2D;

import javax.swing.*;

import misc.utils.Converter;
import models.tikz.TikzComponent;
import models.tikz.TikzDirectedEdge;
import views.editor.canvas.drawables.DrawableTikzEdge;

public class DirectedEdgeDrawer extends EdgeDrawer {
    public DirectedEdgeDrawer() {
        // this was left intentionally blank
    }

    @Override
    public DrawableTikzEdge toDrawable(TikzComponent component, JComponent panel) {
        TikzDirectedEdge edge = (TikzDirectedEdge) component;
        DrawableTikzEdge drawablecomponent = super.toDrawable(edge, panel);
        Point start = fromPosition(edge);
        Point end = toPosition(edge);
        double dy = end.y - start.y;
        double dx = end.x - start.x;
        double theta = Math.atan2(dy, dx);
        double x, y, rho = theta + ARROW_ANGLE;
        x = end.x - ARROW_LENGTH * Math.cos(rho);
        y = end.y - ARROW_LENGTH * Math.sin(rho);
        Point firstHeadPoint = new Point((int) x, (int) y);
        rho = theta - ARROW_ANGLE;
        x = end.x - ARROW_LENGTH * Math.cos(rho);
        y = end.y - ARROW_LENGTH * Math.sin(rho);
        Point secondHeadPoint = new Point((int) x, (int) y);
        Shape secondHeadSegment = new Line2D.Float(Converter.tikz2swing(end, panel), Converter.tikz2swing(secondHeadPoint, panel));
        Shape firstHeadSegment = new Line2D.Float(Converter.tikz2swing(end, panel), Converter.tikz2swing(firstHeadPoint, panel));
        drawablecomponent.addShape(firstHeadSegment);
        drawablecomponent.addShape(secondHeadSegment);
        return drawablecomponent;
    }

}
