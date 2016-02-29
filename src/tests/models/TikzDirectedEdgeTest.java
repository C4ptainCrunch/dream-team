package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jhellinckx on 29/02/16.
 */
public class TikzDirectedEdgeTest {
    TikzDirectedEdge testDirectedEdge;
    TikzNode firstNode;
    TikzNode secondNode;

    @Before
    public void setUp() throws Exception {
        firstNode = new TikzCircle();
        secondNode = new TikzTriangle();
        testDirectedEdge = new TikzDirectedEdge(firstNode, secondNode);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFirstNode() throws Exception{
        assertEquals(firstNode, testDirectedEdge.getFirstNode());
    }

    @Test
    public void testSecondNode() throws Exception{
        assertEquals(secondNode, testDirectedEdge.getSecondNode());
    }

    @Test
    public void testDestination() throws Exception {
        assertEquals(secondNode, testDirectedEdge.destination());
    }
}