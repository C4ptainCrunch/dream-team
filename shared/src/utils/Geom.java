package utils;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by jhellinckx on 26/04/16.
 */
public class Geom {
    /**
     * Computes the euclidean distance between the two points
     * @param first the first point
     * @param second the second point
     * @return the euclidean distance between the two points
     */
    public static double euclideanDistance(Point2D.Float first, Point2D.Float second){
        return Math.sqrt(Math.pow(first.getX() - second.getX(), 2) + Math.pow(first.getY() - second.getY(), 2));
    }

    /**
     * Computes the middle point between the two points
     * @param first the first point
     * @param second the second point
     * @return the middle point between the two points
     */
    public static Point2D.Float middle(Point2D.Float first, Point2D.Float second){
        return new Point2D.Float((first.x + second.x) / 2, (first.y + second.y)/2);
    }
}
