package models.project;

import static org.junit.Assert.assertEquals;

import models.tikz.TikzCircle;
import models.tikz.TikzGraph;
import models.tikz.TikzUndirectedEdge;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import utils.SharedTest;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class ProjectTest extends SharedTest {

    private final long time_diff = 60000L;
    private final String first_diagram_name = "test_diagram";
    private final String second_diagram_name = "second_diagram";
    private final String new_project_path = "second_name";

    private Project project;
    private Diagram diagram;
    private TikzGraph graph;

    private Date getLaterTime(){
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        date.setTime(date.getTime()+time_diff);
       return date;
    }

    private void writeDiff(List<Diff> diff) throws Exception{
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bs);
        os.writeObject(diff);

        project.writeDiff(second_diagram_name, bs.toByteArray());
        os.close();
        bs.close();
    }

    @Before
    public void setUp() throws Exception {
        project = new Project();
        diagram = new Diagram(first_diagram_name, project);
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
        project.writeSource(first_diagram_name, base_source);
        assertEquals(base_source, project.getDiagramSource(first_diagram_name));
    }

    @Test
    public void testLastChange() throws Exception {
        project.writeSource(first_diagram_name, graph.toString());
        diagram.save();
        Diagram second_diagram = new Diagram(second_diagram_name, project);
        second_diagram.save();
        Date date = getLaterTime();
        Diff new_diff = new Diff(date, "");
        List<Diff> diffs = new ArrayList<>(Arrays.asList(new_diff));
        writeDiff(diffs);
        assertEquals(date, project.getLastChange());
    }

    @Test
    public void testMoveProject() throws Exception {
        Path dir = Files.createTempDirectory("test");
        Path p = Paths.get(dir.toString(), new_project_path);
        project.move(p.toFile());
        assertEquals(p, project.getPath());
    }
}
