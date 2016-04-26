package utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.tikz.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bambalaam on 19/04/16.
 */
public class LaTeXBuilderTest {

    TikzGraph graph;
    TikzNode testNode;
    TikzEdge testEdge;
    ArrayList<TikzNode> nodes;
    ArrayList<TikzEdge> edges;
    int length;

    @Before
    public void setUp() throws Exception {
        graph = new TikzGraph();
        length = 4;
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        List<String> refs = Arrays.asList("a", "b", "c", "d");
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

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLatexBuild() throws Exception {
        String expectedString = "\\documentclass{article}\n" + "\\usepackage{tikz}\n" + "\\begin{document}\n"
                + "\\begin{tikzpicture}[x=0.0625em,y=0.0625em]\n" + "\\node[circle, draw](a) at (0,0){};\n"
                + "\\node[circle, draw](b) at (0,1){};\n" + "\\node[circle, draw](c) at (0,2){};\n" + "\\node[circle, draw](d) at (0,3){};\n\n"
                + "\\draw[] (a) -- (b);\n" + "\\draw[] (b) -- (c);\n" + "\\draw[] (c) -- (d);\n" + "\\end{tikzpicture}\n"
                + "\\end{document}\n";
        assertEquals(expectedString, LaTeXBuilder.toLaTeX(graph));

    }

}
