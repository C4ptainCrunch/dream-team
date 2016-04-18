package models;

import constants.Models;
import models.tikz.TikzTriangle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by etnarek on 2/29/16.
 */
public class TikzTriangleTest {
    TikzTriangle triangle;

    @Before
    public void setUp() throws Exception {
        triangle = new TikzTriangle();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetSides() throws Exception {
        int[] sides = {Models.DEFAULT.LENGTH, Models.DEFAULT.LENGTH, Models.DEFAULT.LENGTH};
        int[] triangleSides = {triangle.getSideA(), triangle.getSideB(), triangle.getSideC()};
        assertArrayEquals(sides, triangleSides);
    }

    @Test(expected = NullPointerException.class)
    public void testSetSidesExceptionNULL() throws Exception {
        triangle.setSides(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetSidesExceptionSize() throws Exception {
        int[] sides = {1,2};
        triangle.setSides(sides);
    }

    @Test
    public void testSetSides() throws Exception {
        int[] sides = {1,2,3};
        triangle.setSides(sides);
        assertArrayEquals(sides, triangle.getSides());
    }

    @Test
    public void testCopy() throws Exception {
        int[] sides = {1,2,3};
        triangle.setSides(sides);
        triangle.setPosition(new Point(2,2));
        TikzTriangle o_triangle = new TikzTriangle(triangle);
        assertArrayEquals(triangle.getSides(), o_triangle.getSides());
        assertEquals(triangle.getPosition(), o_triangle.getPosition());
    }

}
