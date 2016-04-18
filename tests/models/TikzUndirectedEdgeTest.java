package models;

import models.tikz.TikzCircle;
import models.tikz.TikzNode;
import models.tikz.TikzRectangle;
import models.tikz.TikzUndirectedEdge;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jhellinckx on 29/02/16.
 */
public class TikzUndirectedEdgeTest {
    TikzUndirectedEdge edge;
    TikzNode first;
    TikzNode second;

    @Before
    public void setUp() throws Exception {
        first = new TikzRectangle();
        second = new TikzCircle();
        edge = new TikzUndirectedEdge(first, second);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetFirstNode() throws Exception {
        assertEquals(first, edge.getFirstNode());
    }

    @Test
    public void testGetSecondNode() throws Exception {
        assertEquals(second, edge.getSecondNode());
    }

    @Test
    public void testCopy() throws Exception{
        TikzUndirectedEdge o_edge = new TikzUndirectedEdge(edge);
        assertEquals(o_edge.getFirstNode(), edge.getFirstNode());
        assertEquals(o_edge.getSecondNode(), edge.getSecondNode());
        assertEquals(o_edge.getToPosition(), edge.getToPosition());
        assertEquals(o_edge.getFromPosition(), edge.getFromPosition());
    }
}
