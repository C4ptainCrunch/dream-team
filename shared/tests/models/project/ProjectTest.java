package models.project;

import static org.junit.Assert.assertEquals;

import models.tikz.TikzCircle;
import models.tikz.TikzGraph;
import models.tikz.TikzUndirectedEdge;
import utils.SharedTest;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.nio.file.Path;
import java.nio.file.Paths;


public class ProjectTest extends SharedTest {

    private final String diagram_name = "test_diagram";

    private Project project;
    private Diagram diagram;
    private TikzGraph graph;

    @Before
    public void setUp() throws Exception {
        project = new Project();
        diagram = new Diagram(diagram_name, project);
        graph = new TikzGraph();
        TikzCircle circle1 = new TikzCircle();
        TikzCircle circle2 = new TikzCircle();
        TikzUndirectedEdge edge = new TikzUndirectedEdge(circle1, circle2);
        graph.add(circle1);
        graph.add(circle2);
        graph.add(edge);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testWriteSource() throws Exception {
        String base_source = graph.toString();
        project.writeSource(diagram_name, base_source);
        assertEquals(base_source, project.getDiagramSource(diagram_name));
    }

    @Test
    public void testLastChange() throws Exception {
        
    }
}
