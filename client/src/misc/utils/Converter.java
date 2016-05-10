package misc.utils;

import java.awt.*;
import java.awt.geom.Point2D;

import javax.swing.*;

public class Converter {
    public static Point2D.Float tikz2swing(Point2D.Float tikzPosition, int panelWidth, int panelHeight) {
        int DPI = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
        double positionX = (tikzPosition.getX() * 10 * DPI)/25.4;
        double positionY = (tikzPosition.getY() * 10 * DPI)/25.4;
        int swingX = (int) (panelWidth / 2 + positionX);
        int swingY = (int) (panelHeight / 2 - positionY);
        return new Point2D.Float(swingX, swingY);
    }

    public static Point2D.Float tikz2swing(Point2D.Float tikzPosition, JComponent panel) {
        return Converter.tikz2swing(tikzPosition, panel.getWidth(), panel.getHeight());
    }

    public static Point2D.Float swing2tikz(Point2D.Float swingPosition, int panelWidth, int panelHeight) {
        int DPI = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
        double tikzX = (swingPosition.getX() - panelWidth / 2);
        double tikzY = (panelHeight / 2 - swingPosition.getY());
        tikzX = (tikzX * 25.4) / (DPI * 10);
        tikzY = (tikzY * 25.4) / (DPI * 10);
        System.out.println((float)tikzX);
        System.out.println((float)tikzY);
        return new Point2D.Float((float)tikzX, (float)tikzY);
    }

    public static Point2D.Float swing2tikz(Point2D.Float swingPosition, JComponent panel) {
        return Converter.swing2tikz(swingPosition, panel.getWidth(), panel.getHeight());
    }
}
