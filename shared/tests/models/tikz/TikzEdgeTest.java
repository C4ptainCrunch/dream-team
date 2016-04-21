package models.tikz;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jhellinckx on 29/02/16.
 */
public class TikzEdgeTest {
    private TikzNode first;
    private TikzNode second;
    private TikzEdge edge;

    @Before
    public void setUp() throws Exception {
        first = new TikzCircle();
        second = new TikzRectangle();
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
