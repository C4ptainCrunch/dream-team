package models;

import constants.Models;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.*;

/**
 * Created by etnarek on 2/29/16.
 */
public class TikzRectangleTest {

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
        assertEquals(rectangle.getWidth(), Models.DEFAULT.LENGTH);
    }

    @Test
    public void testSetWidth() throws Exception {
        int width = 7;
        rectangle.setWidth(width);
        assertEquals(rectangle.getWidth(), width);

    }

    @Test
    public void testGetLength() throws Exception {
        assertEquals(rectangle.getLength(), Models.DEFAULT.LENGTH);

    }

    @Test
    public void testSetLength() throws Exception {
        int length = 9;
        rectangle.setLength(length);
        assertEquals(rectangle.getLength(), length);
    }

    @Test
    public void testCopy() throws Exception {
        rectangle.setLength(5);
        rectangle.setWidth(8);
        rectangle.setColor(Color.BLACK);
        TikzRectangle o_rectangle = new TikzRectangle(rectangle);
        assertEquals(o_rectangle.getLength(), rectangle.getLength());
        assertEquals(o_rectangle.getWidth(), rectangle.getWidth());
        assertEquals(o_rectangle.getColor(), rectangle.getColor());
    }
}
