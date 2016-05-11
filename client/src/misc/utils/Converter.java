package misc.utils;

import java.awt.geom.Point2D;

import javax.swing.*;

public class Converter {
    public static Point2D.Float tikz2swing(Point2D.Float tikzPosition, int panelWidth, int panelHeight) {
        double positionX = centimetersToPixels(tikzPosition.getX());
        double positionY = centimetersToPixels(tikzPosition.getY());
        int swingX = (int) (panelWidth / 2 + positionX);
        int swingY = (int) (panelHeight / 2 - positionY);
        return new Point2D.Float(swingX, swingY);
    }

    public static Point2D.Float tikz2swing(Point2D.Float tikzPosition, JComponent panel) {
        return Converter.tikz2swing(tikzPosition, panel.getWidth(), panel.getHeight());
    }

    public static Point2D.Float swing2tikz(Point2D.Float swingPosition, int panelWidth, int panelHeight) {
        double tikzX = pixelsToCentimeters(swingPosition.getX() - panelWidth / 2);
        double tikzY = pixelsToCentimeters(panelHeight / 2 - swingPosition.getY());
        return new Point2D.Float((float)tikzX, (float)tikzY);
    }

    public static Point2D.Float swing2tikz(Point2D.Float swingPosition, JComponent panel) {
        return Converter.swing2tikz(swingPosition, panel.getWidth(), panel.getHeight());
    }

    public static double centimetersToPixels(double centimeters){
        int DPI = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
        return  (centimeters *  DPI)/2.54;
    }

    public static double pixelsToCentimeters(double pixels){
        int DPI = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
        return (pixels * 2.54) / (DPI);
    }
}
