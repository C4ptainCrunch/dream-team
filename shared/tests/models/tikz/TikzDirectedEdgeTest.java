package models.tikz;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.SharedTest;

/**
 * Created by jhellinckx on 29/02/16.
 */
public class TikzDirectedEdgeTest extends SharedTest {
    TikzDirectedEdge testDirectedEdge;
    TikzNode firstNode;
    TikzNode secondNode;

    @Before
    public void setUp() throws Exception {
        firstNode = new TikzCircle();
        secondNode = new TikzPolygon();
        testDirectedEdge = new TikzDirectedEdge(firstNode, secondNode);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFirstNode() throws Exception {
        assertEquals(firstNode, testDirectedEdge.getFirstNode());
    }

    @Test
    public void testSecondNode() throws Exception {
        assertEquals(secondNode, testDirectedEdge.getSecondNode());
    }

    @Test
    public void testDestination() throws Exception {
        assertEquals(secondNode, testDirectedEdge.destination());
    }

    @Test
    public void testCopy() throws Exception {
        TikzDirectedEdge o_edge = new TikzDirectedEdge(testDirectedEdge);
        assertEquals(o_edge.getFirstNode(), testDirectedEdge.getFirstNode());
        assertEquals(o_edge.getSecondNode(), testDirectedEdge.getSecondNode());
        assertEquals(o_edge.getToPosition(), testDirectedEdge.getToPosition());
        assertEquals(o_edge.getFromPosition(), testDirectedEdge.getFromPosition());
    }
}
