package models;

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
        first = new TikzRectancle();
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
}