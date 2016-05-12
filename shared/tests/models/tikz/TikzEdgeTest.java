package models.tikz;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Geom;
import utils.SharedTest;

/**
 * Created by jhellinckx on 29/02/16.
 */
public class TikzEdgeTest extends SharedTest {
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

    @Test
    public void testGetPosition() throws Exception {
        Point2D.Float first_node_position = new Point2D.Float(5, 5);
        Point2D.Float second_node_position = new Point2D.Float(8, 8);
        first.setPosition(first_node_position);
        second.setPosition(second_node_position);
        Point2D.Float expected_edge_position = Geom.middle(first_node_position, second_node_position);
        assertEquals(expected_edge_position, edge.getPosition());
    }
}
