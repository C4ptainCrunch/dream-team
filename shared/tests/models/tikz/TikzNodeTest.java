package models.tikz;

import static org.junit.Assert.assertEquals;

import java.awt.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        Point point = new Point(1, 1);
        tikzNode.setPosition(point);
        assertEquals(tikzNode.getPosition(), point);
    }

    @Test
    public void testMove() throws Exception {
        tikzNode.move(1, 1);
        assertEquals(tikzNode.getPosition(), new Point(1, 1));
    }

    @Test
    public void testTranslate() throws Exception {
        Point start = new Point(1, 1);
        int dx = 2;
        int dy = 3;
        tikzNode.setPosition(new Point(start));
        tikzNode.translate(dx, dy);

        assertEquals(start.x + dx, tikzNode.getPosition().x);
        assertEquals(start.y + dy, tikzNode.getPosition().y);
    }
}
