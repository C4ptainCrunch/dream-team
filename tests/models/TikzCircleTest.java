package models;

import constants.Models;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by etnarek on 2/29/16.
 */
public class TikzCircleTest {

    TikzCircle circle;

    @Before
    public void setUp() throws Exception {
        circle = new TikzCircle();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetRadius() throws Exception {
        assertEquals(circle.getRadius(), Models.DEFAULT.LENGTH);
    }

    @Test
    public void testSetRadius() throws Exception {
        int radius = 5;
        circle.setRadius(radius);
        assertEquals(circle.getRadius(), radius);
    }

    @Test
    public void testCopy() throws Exception {
        circle.setRadius(5);
        circle.setLabel("slt");
        TikzCircle o_circle = new TikzCircle(circle);
        assertEquals(o_circle.getRadius(), circle.getRadius());
        assertEquals(o_circle.getLabel(), circle.getLabel());
    }
}