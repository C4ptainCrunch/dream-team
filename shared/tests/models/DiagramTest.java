package models;

import static org.junit.Assert.*;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.project.Diagram;
import models.project.Diff;
import models.project.Project;
import models.tikz.TikzCircle;
import models.tikz.TikzGraph;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

public class DiagramTest {
    private Project project;

    class ProjectMock extends Project {
        private Path source;
        private Path diff;

        public ProjectMock() throws IOException {
            this.source = File.createTempFile("source", null).toPath();
            this.diff = File.createTempFile("diff", null).toPath();
        }

        public Path getDiagramSource(String name) {
            return this.source;
        }

        public Path getDiagramDiff(String name) {
            return this.diff;
        }
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private Diagram getEmptyDiagram() throws IOException {
        return new Diagram("my-project", new ProjectMock());
    }

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
        File save = project.getDiagramSource("my-diagram").toFile();
        PrintWriter writer = new PrintWriter(save);
        writer.print("\\node[circle, draw]() at (0,0) {test-label}");
        writer.close();

        Diagram d = new Diagram("my-diagram", project);

        assertEquals(d.getGraph().getNodes().size(), 1);

    }

    @Test
    public void testGetName() throws Exception {
        Diagram d = new Diagram("my-diagram", project);
        assertEquals(d.getName(), "my-diagram");
    }
//
//    @Test
//    public void testSave() throws Exception {
//        // TODO
//    }
//
    @Test
    public void testGetDiskTikz() throws Exception {
        File save = project.getDiagramSource("my-dia").toFile();
        PrintWriter writer = new PrintWriter(save);
        final String tikz = "\\node[circle, draw]() at (0,0) {test-label}";
        writer.print(tikz);
        writer.close();

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

        project.sync();
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

        FileOutputStream fs = new FileOutputStream(d.getDiffPath().toFile());
        ObjectOutputStream os = new ObjectOutputStream(fs);
        os.writeObject(diffs);
        os.close();
        fs.close();

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
        project.sync();

        FileInputStream fs = new FileInputStream(d.getDiffPath().toFile());
        ObjectInputStream os = new ObjectInputStream(fs);
        List<Diff> writtenDiffs = (List<Diff>) os.readObject();
        os.close();
        fs.close();

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
