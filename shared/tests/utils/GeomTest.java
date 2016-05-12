package utils;

import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.*;

public class GeomTest {

    private final float precision = 0.001f;

    @Test
    public void testEuclideanDistance() throws Exception {
        Point2D.Float first_point = new Point2D.Float(0.5f, 2.2f);
        Point2D.Float second_point = new Point2D.Float(4.1f, 3.25f);
        float expected_ed = 3.749f;
        assertEquals(Geom.euclideanDistance(first_point, second_point), expected_ed, precision);
    }

    @Test
    public void testMiddle() throws Exception {
        Point2D.Float first_point = new Point2D.Float(2, 4);
        Point2D.Float second_point = new Point2D.Float(4, 8);
        Point2D.Float expected_middle = new Point2D.Float(3, 6);
        assertEquals(Geom.middle(first_point, second_point), expected_middle);
    }
}