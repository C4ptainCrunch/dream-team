package views.editor.canvas.drawers;

import java.awt.*;
import java.awt.geom.Line2D;

import javax.swing.*;

import misc.utils.Converter;
import models.tikz.TikzComponent;
import models.tikz.TikzEdge;
import views.editor.canvas.drawables.DrawableTikzEdge;
import constants.Models;

public abstract class EdgeDrawer extends ComponentDrawer {

    public EdgeDrawer() {
        // this was left intentionally blank
    }

    @Override
    public DrawableTikzEdge toDrawable(TikzComponent component, JComponent panel) {
        TikzEdge edge = (TikzEdge) component;
        DrawableTikzEdge drawableComponent = new DrawableTikzEdge(component);
        drawableComponent
                .addShape(new Line2D.Float(fromPosition(edge, panel), toPosition(edge, panel)));
        return drawableComponent;
    }

    public Point fromPosition(TikzEdge edge, JComponent panel) {
        Point start;
        if (edge.getFirstNode() == null || edge.getSecondNode() == null) {
            start = Converter.tikz2swing(new Point(-Models.DEFAULT.EDGE_X_LENGTH / 2, 0), panel);
        } else {
            // Get closest anchor
            start = Drawer.closestAnchor(edge.getFirstNode(), edge.getToPosition(), panel);
        }
        return start;
    }

    public Point toPosition(TikzEdge edge, JComponent panel) {
        Point end;
        if (edge.getFirstNode() == null || edge.getSecondNode() == null) {
            end = Converter.tikz2swing(new Point(Models.DEFAULT.EDGE_X_LENGTH / 2, 0), panel);
        } else {
            end = Drawer.closestAnchor(edge.getSecondNode(), edge.getFromPosition(), panel);
        }
        return end;
    }
}
