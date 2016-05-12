package models.tikz;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.SharedTest;

/**
 * Created by jhellinckx on 01/03/16.
 */
public class TikzGraphTest extends SharedTest {
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
            assertEquals(graph.size(), i + 1);
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
        while (it.hasNext()) {
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

        graph.addAllEdges(edges);
        assertArrayEquals(edges.toArray(), graph.get(firstNode).toArray());

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
            edges.add(new TikzDirectedEdge(node1, node2));
        }
        graph.addAllEdges(edges);
        while (!edges.isEmpty()) {
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
            edges1.add(new TikzDirectedEdge(node1, nodetmp));
        }
        graph.addAllEdges(edges1);
        assertArrayEquals(graph.get(node1).toArray(), edges1.toArray());

        TikzNode node2 = new TikzRectangle();
        List<TikzEdge> edges2 = new ArrayList<TikzEdge>();
        for (int i = 0; i < 5; i++) {
            TikzNode nodetmp = new TikzCircle();
            edges2.add(new TikzDirectedEdge(node2, nodetmp));
        }
        graph.addAllEdges(edges2);
        assertArrayEquals(graph.get(node2).toArray(), edges2.toArray());

        graph.remove(node1);
        assert graph.getNodes().contains(node2);
        assert !graph.getNodes().contains(node1);
        assertArrayEquals(graph.getEdges().toArray(), edges2.toArray());

    }

    @Test
    public void testAddNode() throws Exception {
        int length = 4;
        TikzGraph graph = new TikzGraph();
        ArrayList<TikzNode> nodes = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            TikzNode node = new TikzCircle();
            graph.add(node);
            nodes.add(node);
            assert graph.getNodes().contains(node);
        }
        for (TikzNode node : nodes) {
            graph.add(node);
        }

        assertEquals(graph.getNodes().size(), length);
    }

    @Test
    public void testAddEdge() throws Exception {
        TikzGraph graph = new TikzGraph();
        TikzNode node1 = new TikzCircle();
        TikzNode node2 = new TikzCircle();
        TikzEdge edge = new TikzDirectedEdge(node1, node2);
        graph.add(node1);
        graph.add(node2);
        graph.add(edge);
        assert graph.getEdges().contains(edge);
        assertEquals(graph.getNodes().size(), 2);
        graph.add(edge);
        assertEquals(graph.getEdges().size(), 1);

        TikzNode node3 = new TikzPolygon();
        TikzNode node4 = new TikzText();
        TikzEdge edge2 = new TikzUndirectedEdge(node3, node4);
        graph.add(edge2);
        assert graph.getNodes().contains(node3);
        assert graph.getNodes().contains(node4);
    }

    @Test
    public void testAddGraph() throws Exception {
        int length = 5;
        TikzGraph graph1 = new TikzGraph();
        TikzGraph graph2 = new TikzGraph();

        for (int i = 0; i < length; i++) {
            TikzEdge edge = new TikzDirectedEdge(new TikzRectangle(), new TikzCircle());
            graph1.add(edge);
        }

        for (int i = 0; i < length; i++) {
            TikzEdge edge = new TikzDirectedEdge(new TikzRectangle(), new TikzCircle());
            graph2.add(edge);
        }

        graph2.add(graph1);
        assert graph2.getEdges().containsAll(graph1.getEdges());
        assert graph2.getNodes().containsAll(graph1.getNodes());

        assertEquals(graph2.getEdges().size(), length * 2);

    }

    @Test
    public void testGetEdges() throws Exception {
        int length = 4;
        ArrayList<TikzEdge> edges = new ArrayList<>();
        TikzGraph graph = new TikzGraph();
        for (int i = 0; i < length; i++) {
            TikzNode node1 = new TikzCircle();
            TikzNode node2 = new TikzRectangle();
            TikzEdge edge = new TikzDirectedEdge(node1, node2);
            graph.add(edge);
            edges.add(edge);
        }

        assertArrayEquals(graph.getEdges().toArray(), edges.toArray());

    }

    @Test
    public void testAddAllNodes() throws Exception {
        int length = 4;
        ArrayList<TikzNode> nodes = new ArrayList<>();
        TikzGraph graph = new TikzGraph();

        for (int i = 0; i < length; i++) {
            nodes.add(new TikzCircle());
        }

        graph.addAllNodes(nodes);

        assertArrayEquals(graph.getNodes().toArray(), nodes.toArray());

    }

    @Test
    public void testObservableConstructor() throws Exception {
        assertTrue(!graph.hasChanged());
        assertEquals(0, graph.countObservers());
    }

    @Test
    public void testNodeAddedAsObserved() throws Exception {
        TikzCircle testNode = new TikzCircle();
        graph.add(testNode);
        assertEquals(1, testNode.countObservers());
    }

    @Test
    public void testNodeNotifiesGraph() throws Exception {

        TikzGraph anonymous = new TikzGraph() {
            boolean flag = false;

            @Override
            public void update(Observable obs, Object o) {
                flag = true;
            }

            public boolean getFlag() {
                return flag;
            }
        };

        TikzCircle testNode = new TikzCircle();
        anonymous.add(testNode);
        assertFalse((boolean) anonymous.getClass().getMethod("getFlag").invoke(anonymous));
        testNode.move(2, 2);
        assertTrue((boolean) anonymous.getClass().getMethod("getFlag").invoke(anonymous));
    }

    @Test
    public void testTranslation() throws Exception {
        List<TikzNode> nodes = new ArrayList<>();
        List<Point2D.Float> starts = new ArrayList<>();
        int dx = -7;
        int dy = 3;
        int len = 4;

        for (int i = 0; i < len; i++) {
            Point2D.Float p = new Point2D.Float(i + 6, i - 2);
            TikzNode node = new TikzPolygon();
            node.setPosition(new Point2D.Float(p.x, p.y));
            nodes.add(node);
            starts.add(p);
        }
        graph.addAllNodes(nodes);

        graph.translation(dx, dy);

        for (int i = 0; i < len; i++) {
            TikzNode node = graph.getNodes().get(i);
            Point2D.Float p = node.getPosition();
            Point2D.Float start = starts.get(i);
            assertEquals(start.x + dx, p.x, 0.01);
            assertEquals(start.y + dy, p.y, 0.01);
        }
    }

    @Test
    public void testCopy() throws Exception {
        graph.add(new TikzCircle());
        TikzGraph o_graph = graph.getClone();
        assertEquals(graph.size(), o_graph.size());
        assertNotEquals(graph.getNodes(), o_graph.getNodes());
    }

    @Test
    public void testLatexBuild() throws Exception {
        TikzGraph graph = new TikzGraph();
        int length = 4;
        ArrayList<String> refs = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        ArrayList<TikzNode> nodes = new ArrayList<>();
        ArrayList<TikzEdge> edges = new ArrayList<>();
        TikzNode testNode;
        for (int i = 0; i < length; i++) {
            testNode = new TikzCircle(refs.get(i));
            testNode.move(0, i);
            nodes.add(testNode);
        }

        TikzEdge testEdge;
        for (int i = 0; i < length - 1; i++) {
            testEdge = new TikzUndirectedEdge(nodes.get(i), nodes.get(i + 1));
            edges.add(testEdge);
        }

        graph.addAllNodes(nodes);
        graph.addAllEdges(edges);

        String expectedString = "\\documentclass{article}\n" + "\\usepackage{tikz}\n" + "\\usetikzlibrary{shapes.geometric}\n"
                + "\\begin{document}\n" + "\\begin{tikzpicture}\n" + "\\node[circle, draw](a) at (0.000,0.000){};\n"
                + "\\node[circle, draw](b) at (0.000,1.000){};\n" + "\\node[circle, draw](c) at (0.000,2.000){};\n"
                + "\\node[circle, draw](d) at (0.000,3.000){};\n" + "\\draw[] (a) -- (b);\n" + "\\draw[] (b) -- (c);\n"
                + "\\draw[] (c) -- (d);\n" + "\\end{tikzpicture}\n" + "\\end{document}\n";

        assertEquals(expectedString, graph.toLatex());

    }

    @Test
    public void testContainsEdgeAndNode() throws Exception {
        TikzNode firstNode = new TikzCircle();
        TikzNode secondNode = new TikzRectangle();
        TikzEdge edge = new TikzUndirectedEdge(firstNode, secondNode);
        graph.add(firstNode);
        graph.add(secondNode);
        graph.add(edge);
        assertTrue(graph.contains(edge));
        assertTrue(graph.contains(firstNode));
        assertTrue(graph.contains(secondNode));
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(graph.isEmpty());
        graph.add(new TikzCircle());
        assertFalse(graph.isEmpty());
    }
}
