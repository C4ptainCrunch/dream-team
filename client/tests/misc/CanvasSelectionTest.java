package misc;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import misc.utils.ClientTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhellinckx on 12/05/16.
 */
public class CanvasSelectionTest extends ClientTest {

    private final float precision = 0.0001f;

    private CanvasSelection selection;
    private Point2D.Float up_left;
    private Point2D.Float bottom_right;

    @Before
    public void setUp() throws Exception {
        up_left = new Point2D.Float(5f, 5f);
        selection = new CanvasSelection(up_left);
        bottom_right = new Point2D.Float(15f, 15f);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testResize() throws Exception {
        selection.resize(bottom_right);
        double expected_width = bottom_right.getX() - up_left.getX();
        double expected_height = bottom_right.getY() - up_left.getY();
        assertEquals(selection.getWidth(), expected_width, precision);
        assertEquals(selection.getHeight(), expected_height, precision);
    }
}
