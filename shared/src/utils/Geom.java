package utils;

import java.awt.*;

/**
 * Created by jhellinckx on 26/04/16.
 */
public class Geom {
    public static double euclideanDistance(Point first, Point second){
        return Math.sqrt(Math.pow(first.getX() - second.getX(), 2) + Math.pow(first.getY() - second.getY(), 2));
    }
}
