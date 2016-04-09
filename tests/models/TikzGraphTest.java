package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jhellinckx on 01/03/16.
 */
public class TikzGraphTest {
    TikzGraph graph;

    @Before
    public void setUp() throws Exception {
        graph = new TikzGraph();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSize() throws Exception {
        for (int i = 0; i < 6; i++) {
            TikzNode node = new TikzCircle();
            graph.add(node);
            assertEquals(graph.size(), i+1);
        }
    }

    @Test
    public void testIterator() throws Exception {
        List<TikzNode> nodes = new ArrayList<TikzNode>();
        for (int i = 0; i < 5; i++) {
            TikzNode circle = new TikzCircle();
            nodes.add(circle);
            graph.add(circle);
        }
        Iterator<TikzNode> it = graph.iterator();
        while(it.hasNext()){
            TikzNode next = it.next();
            assertTrue(nodes.contains(next));
            nodes.remove(next);
        }
    }

    @Test
    public void testAddWithoutEdge() throws Exception {
        TikzNode key = new TikzCircle();
        assertEquals(graph.get(key), new ArrayList<TikzEdge>());
    }

    @Test
    public void testAddAllEdges() throws Exception {
        TikzNode firstNode = new TikzRectangle();
        TikzNode secondNode = new TikzCircle();
        List<TikzEdge> edges = new ArrayList<TikzEdge>();
        for (int i = 0; i < 5; i++) {
            edges.add(new TikzUndirectedEdge(firstNode, secondNode));
        }

        graph.addAll(edges);
        assertEquals(edges.toArray(), graph.get(firstNode).toArray());

    }

    @Test
    public void testAddWithEdge() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {

    }

    @Test
    public void testGetNodes() throws Exception {

    }

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testRemove() throws Exception {

    }

    @Test
    public void testRemove1() throws Exception {
        TikzNode node = new TikzRectangle();
        List<TikzEdge> edges = new ArrayList<TikzEdge>();
        for (int i = 0; i < 5; i++) {
            TikzNode node1 = new TikzRectangle();
            TikzNode node2 = new TikzCircle();
            edges.add(new TikzDirectedEdge(node1,node2));
        }
        graph.addAll(edges);
        while (!edges.isEmpty()){
            TikzEdge edge = edges.remove(0);
            edges.remove(edge);
            graph.remove(edge);
            assertArrayEquals(graph.getEdges().toArray(), edges.toArray());
        }
        assertEquals(graph.get(node).toArray().length, 0);
    }

    @Test
    public void testRemove2() throws Exception {
        TikzNode node1 = new TikzRectangle();
        List<TikzEdge> edges1 = new ArrayList<TikzEdge>();
        for (int i = 0; i < 5; i++) {
            TikzNode nodetmp = new TikzCircle();
            edges1.add(new TikzDirectedEdge(node1,nodetmp));
        }
        graph.addAll(edges1);
        assertArrayEquals(graph.get(node1).toArray(), edges1.toArray());

        TikzNode node2 = new TikzRectangle();
        List<TikzEdge> edges2 = new ArrayList<TikzEdge>();
        for (int i = 0; i < 5; i++) {
            TikzNode nodetmp = new TikzCircle();
            edges2.add(new TikzDirectedEdge(node2,nodetmp));
        }
        graph.addAll(edges2);
        assertArrayEquals(graph.get(node2).toArray(), edges2.toArray());

        graph.remove(node1);
        assert graph.getNodes().contains(node2);
        assert !graph.getNodes().contains(node1);
        assertArrayEquals(graph.getEdges().toArray(), edges2.toArray());

    }
}
