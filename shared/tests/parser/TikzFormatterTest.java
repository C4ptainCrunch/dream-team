package parser;

import models.tikz.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by jhellinckx on 26/04/16.
 */
public class TikzFormatterTest {
    private TikzGraph graph;
    @Before
    public void setUp() throws Exception {
        graph = new TikzGraph();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        int length = 4;
        TikzNode testNode;
        TikzEdge testEdge;
        List<String> refs = Arrays.asList("a","b","c","d");

        ArrayList<TikzNode> nodes = new ArrayList<>();
        ArrayList<TikzEdge> edges = new ArrayList<>();

        String resultString = "\\node[circle, draw](a) at (0,0){};\n" + "\\node[circle, draw](b) at (0,1){};\n"
                + "\\node[circle, draw](c) at (0,2){};\n" + "\\node[circle, draw](d) at (0,3){};\n\n" + "\\draw[] (a) -- (b);\n"
                + "\\draw[] (b) -- (c);\n" + "\\draw[] (c) -- (d);\n";


        for (int i = 0; i < length; i++) {
            testNode = new TikzCircle(refs.get(i));
            testNode.move(0, i);
            nodes.add(testNode);
        }

        for (int i = 0; i < length - 1; i++) {
            testEdge = new TikzUndirectedEdge(nodes.get(i), nodes.get(i + 1));
            edges.add(testEdge);
        }

        graph.addAllNodes(nodes);
        graph.addAllEdges(edges);
        assertEquals(graph.toString(), resultString);
    }
}
