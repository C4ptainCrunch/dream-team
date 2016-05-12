package misc.utils;

import java.awt.geom.Point2D;

import javax.swing.*;

public class Converter {
    public static Point2D.Float tikz2swing(final Point2D.Float tikzPosition, final int panelWidth, final int panelHeight) {
        double positionX = centimetersToPixels(tikzPosition.getX());
        double positionY = centimetersToPixels(tikzPosition.getY());
        int swingX = (int) (panelWidth / 2 + positionX);
        int swingY = (int) (panelHeight / 2 - positionY);
        return new Point2D.Float(swingX, swingY);
    }

    public static Point2D.Float tikz2swing(final Point2D.Float tikzPosition, final JComponent panel) {
        return Converter.tikz2swing(tikzPosition, panel.getWidth(), panel.getHeight());
    }

    public static Point2D.Float swing2tikz(final Point2D.Float swingPosition, final int panelWidth, final int panelHeight) {
        double tikzX = pixelsToCentimeters(swingPosition.getX() - panelWidth / 2);
        double tikzY = pixelsToCentimeters(panelHeight / 2 - swingPosition.getY());
        return new Point2D.Float((float) tikzX, (float) tikzY);
    }

    public static Point2D.Float swing2tikz(final Point2D.Float swingPosition, final JComponent panel) {
        return Converter.swing2tikz(swingPosition, panel.getWidth(), panel.getHeight());
    }

    public static double centimetersToPixels(final double centimeters) {
        int DPI = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
        return (centimeters * DPI) / 2.54;
    }

    public static double pixelsToCentimeters(final double pixels) {
        int DPI = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
        return (pixels * 2.54) / (DPI);
    }
}
