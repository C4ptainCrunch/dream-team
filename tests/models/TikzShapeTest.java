package models;

import constants.Models;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by etnarek on 2/29/16.
 */
public class TikzShapeTest {

    TikzShape shape;
    @Before
    public void setUp() throws Exception {
        shape = new TikzCircle();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetOutlineColor() throws Exception {
        assertEquals(shape.getOutlineColor(), Models.DEFAULT.COLOR);
    }

    @Test
    public void testSetOutlineColor() throws Exception {
        shape.setOutlineColor(Color.BLACK);
        assertEquals(shape.getOutlineColor(), Color.BLACK);
    }

    @Test
    public void testGetOutlineWidth() throws Exception {
        assertEquals(shape.getOutlineWidth(), Models.DEFAULT.WIDTH);
    }

    @Test
    public void testSetOutlineWidth() throws Exception {
        int width = 5;
        shape.setOutlineWidth(width);
        assertEquals(shape.getOutlineWidth(), width);
    }
}