package utils;

import java.awt.*;

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
    public static double euclideanDistance(Point first, Point second){
        return Math.sqrt(Math.pow(first.getX() - second.getX(), 2) + Math.pow(first.getY() - second.getY(), 2));
    }

    /**
     * Computes the middle point between the two points
     * @param first the first point
     * @param second the second point
     * @return the middle point between the two points
     */
    public static Point middle(Point first, Point second){
        return new Point((first.x + second.x) / 2, (first.y + second.y)/2);
    }
}
