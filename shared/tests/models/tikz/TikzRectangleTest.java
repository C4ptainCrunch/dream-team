package models.tikz;

import static org.junit.Assert.assertEquals;

import java.awt.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.SharedTest;
import constants.Models;

/**
 * Created by etnarek on 2/29/16.
 */
public class TikzRectangleTest extends SharedTest {

    TikzRectangle rectangle;

    @Before
    public void setUp() throws Exception {
        rectangle = new TikzRectangle();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetWidth() throws Exception {
        assertEquals(rectangle.getWidth(), Models.DEFAULT.LENGTH, 0.01);
    }

    @Test
    public void testSetWidth() throws Exception {
        int width = 7;
        rectangle.setWidth(width);
        assertEquals(rectangle.getWidth(), width, 0.01);

    }

    @Test
    public void testGetLength() throws Exception {
        assertEquals(rectangle.getLength(), Models.DEFAULT.LENGTH, 0.01);

    }

    @Test
    public void testSetLength() throws Exception {
        int length = 9;
        rectangle.setLength(length);
        assertEquals(rectangle.getLength(), length, 0.01);
    }

    @Test
    public void testCopy() throws Exception {
        rectangle.setLength(5);
        rectangle.setWidth(8);
        rectangle.setStrokeColor(Color.BLACK);
        TikzRectangle o_rectangle = new TikzRectangle(rectangle);
        assertEquals(o_rectangle.getLength(), rectangle.getLength(), 0.01);
        assertEquals(o_rectangle.getWidth(), rectangle.getWidth(), 0.01);
        assertEquals(o_rectangle.getStrokeColor(), rectangle.getStrokeColor());
    }
}
