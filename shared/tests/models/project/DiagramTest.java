package models.project;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utils.SharedTest;
import models.tikz.TikzCircle;
import models.tikz.TikzGraph;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DiagramTest extends SharedTest {
    private Project project;

    class ProjectMock extends Project {
        public Path source;
        public Path diff;

        public ProjectMock() throws IOException {
            this.source = File.createTempFile("source", null).toPath();
            this.diff = File.createTempFile("diff", null).toPath();
        }

        synchronized public String getDiagramSource(String name) throws IOException {
            return new String(Files.readAllBytes(this.source));
        }

        synchronized public byte[] getDiagramDiff(String name) throws IOException {
            return Files.readAllBytes(this.diff);
        }

        synchronized public void writeDiff(String name, byte[] bytes) throws IOException {
            Files.write(this.diff, bytes, TRUNCATE_EXISTING, CREATE);
        }

        synchronized public void writeSource(String name, String tikz) throws IOException {
            Files.write(this.source, tikz.getBytes(), TRUNCATE_EXISTING, CREATE);
        }

        synchronized public void renameDiagram(String oldName, String newName) throws IOException {
            // Do not perform any IO operations
        }
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        project = new ProjectMock();
    }

    @After
    public void tearDown() throws Exception {
        project = null;
    }

    @Test
    public void testToString() throws Exception {
        Diagram d = new Diagram("my-project", project);
        assertEquals(d.toString(), "my-project");
    }

    @Test
    public void testGetGraph() throws Exception {
        final String tikz = "\\node[circle, draw]() at (0,0) {test-label}";
        project.writeSource("my-dia", tikz);

        Diagram d = new Diagram("my-diagram", project);

        assertEquals(d.getGraph().getNodes().size(), 1);

    }

    @Test
    public void testGetName() throws Exception {
        Diagram d = new Diagram("my-diagram", project);
        assertEquals(d.getName(), "my-diagram");
    }

    @Test
    public void testGetDiskTikz() throws Exception {
        final String tikz = "\\node[circle, draw]() at (0,0) {test-label}";
        project.writeSource("my-dia", tikz);

        Diagram d = new Diagram("my-dia", project);
        TikzGraph g = d.getGraph();
        g.add(new TikzCircle(10));

        assertEquals(tikz, d.getDiskTikz());
        assertNotEquals(tikz, g.toString());
    }

    @Test
    public void testRename() throws Exception {
        Diagram d = new Diagram("my-dia", project);
        d.rename("my-new-dia");
        assertEquals(d.getName(), "my-new-dia");
    }

    @Test
    public void testGetLastChange() throws Exception {
        Diagram dia = new Diagram("my-dia", project);
        dia.save();

        List<Diff> diffs = new ArrayList<>();
        Date d = new Date(1000);
        diffs.add(new Diff(d, "diff one"));

        Date d2 = new Date(2000);
        diffs.add(new Diff(d2, "diff 2"));

        Method m = dia.getClass().getDeclaredMethod("writeDiffs", List.class);
        m.setAccessible(true);

        m.invoke(dia, diffs);

        assertEquals(d2, dia.getLastChange());
    }

    @Test
    public void testGetProject() throws Exception{
        Diagram d = new Diagram("", project);
        assertEquals(d.getProject(), project);
    }

    @Test
    public void testIsTemporary() throws Exception{
        Diagram d = new Diagram("", project);
        assertEquals(d.isTemporary(), project.isTemporary());
    }

    @Test
    public void testGetDiffs() throws Exception {
        Diagram d = new Diagram("my-dia", project);
        d.save();
        List<Diff> diffs = new ArrayList<>();
        diffs.add(new Diff(new Date(0), "diff one"));

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bs);
        os.writeObject(diffs);

        project.writeDiff("my-dia", bs.toByteArray());

        os.close();
        bs.close();


        List<Diff> readDiffs = d.getDiffs();
        assertEquals(readDiffs.get(0).getDate(), new Date(0));
        assertEquals(readDiffs.get(0).getPatch(), "diff one");
    }

    @Test
    public void testWriteDiffs() throws Exception {
        Diagram d = new Diagram("my-dia", project);
        d.save();

        List<Diff> diffs = new ArrayList<>();
        diffs.add(new Diff(new Date(0), "diff one"));

        Method m = d.getClass().getDeclaredMethod("writeDiffs", List.class);
        m.setAccessible(true);

        m.invoke(d, diffs);

        ByteArrayInputStream bs = new ByteArrayInputStream(project.getDiagramDiff("my-dia"));
        ObjectInputStream os = new ObjectInputStream(bs);

        List<Diff> writtenDiffs = (List<Diff>) os.readObject();
        os.close();
        bs.close();

        assertEquals(writtenDiffs.get(0).getDate(), new Date(0));
        assertEquals(writtenDiffs.get(0).getPatch(), "diff one");
    }


    @Test
    public void testUndoRedo() throws Exception {
        Diagram d = new Diagram("my-dia", project);
        d.save();

        TikzGraph graph = d.getGraph();
        graph.add(new TikzCircle(4));
        d.save();

        assertEquals(d.getGraph().size(), 1);
        List<Diff> listDiff = d.getDiffs();

        d.undo();

        assertEquals(listDiff.size()-1, d.getDiffs().size());
        assertEquals(d.getGraph().size(), 0);

        d.redo();
        assertEquals(listDiff.size(), d.getDiffs().size());
        assertEquals(d.getGraph().size(), 1);
        }
}
