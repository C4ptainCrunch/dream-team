package misc.utils;

import java.awt.*;
import java.awt.geom.Point2D;

import javax.swing.*;

/**
 * Created by jhellinckx on 19/04/16.
 */

public class Converter {
    public static Point2D.Float tikz2swing(Point2D.Float tikzPosition, int panelWidth, int panelHeight) {
        int swingX = (int) (panelWidth / 2 + tikzPosition.getX());
        int swingY = (int) (panelHeight / 2 - tikzPosition.getY());
        return new Point2D.Float(swingX, swingY);
    }

    public static Point2D.Float tikz2swing(Point2D.Float tikzPosition, JComponent panel) {
        return Converter.tikz2swing(tikzPosition, panel.getWidth(), panel.getHeight());
    }

    public static Point2D.Float swing2tikz(Point2D.Float swingPosition, int panelWidth, int panelHeight) {
        int tikzX = (int) (swingPosition.getX() - panelWidth / 2);
        int tikzY = (int) (panelHeight / 2 - swingPosition.getY());
        return new Point2D.Float(tikzX, tikzY);
    }

    public static Point2D.Float swing2tikz(Point2D.Float swingPosition, JComponent panel) {
        return Converter.swing2tikz(swingPosition, panel.getWidth(), panel.getHeight());
    }
}
