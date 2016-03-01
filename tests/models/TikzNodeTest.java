package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by jhellinckx on 29/02/16.
 */
public class TikzNodeTest {
    private TikzNode tikzNode;

    @Before
    public void setUp() throws Exception {
        tikzNode = new TikzCircle();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testPosition() throws Exception {
        Point point = new Point(1,1);
        tikzNode.setPosition(point);
        assertEquals(tikzNode.getPosition(), point);
    }

    @Test
    public void testMove() throws Exception {
        tikzNode.move(1,1);
        assertEquals(tikzNode.getPosition(), new Point(1,1));
    }
}