//package models;
//
//import static org.junit.Assert.*;
//
//import java.io.*;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import models.project.Diagram;
//import models.project.Diff;
//import models.project.Project;
//import models.tikz.TikzCircle;
//import models.tikz.TikzGraph;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.TemporaryFolder;
//
//import constants.Models;
//
//public class DiagramTest {
//
//    class ProjectMock extends Project {
//        private Path source;
//        private Path diff;
//
//        public ProjectMock() throws IOException {
//            this.source = File.createTempFile("source", null).toPath();
//            this.diff = File.createTempFile("diff", null).toPath();
//        }
//
//        public Path getDiagramSource(String name) {
//            return this.source;
//        }
//
//        public Path getDiagramDiff(String name) {
//            return this.diff;
//        }
//    }
//
//    @Rule
//    public TemporaryFolder folder = new TemporaryFolder();
//
//    private Diagram getEmptyDiagram() throws IOException {
//        return new Diagram("my-project", new ProjectMock());
//    }
//
//    @Test
//    public void testInitialize() throws Exception {
//        File path = new File(folder.getRoot(), "my-project");
//        assertFalse(path.exists());
//
//        Diagram p = new Diagram(path.toPath());
//        assertFalse(path.exists());
//        p.save();
//
//        assertTrue(path.exists());
//        assertTrue(new File(path, Models.Project.SAVE_FILE).exists());
//    }
//
//    @Test
//    public void testToString() throws Exception {
//        Diagram p = getEmptyProject();
//        assertEquals(p.toString(), "my-project");
//    }
//
//    @Test
//    public void testGetGraph() throws Exception {
//        File save = folder.newFile(Models.Project.SAVE_FILE);
//        PrintWriter writer = new PrintWriter(save);
//        writer.print("\\node[circle, draw]() at (0,0) {test-label}");
//        writer.close();
//
//        Diagram p = new Diagram(folder.getRoot().toPath());
//
//        assertEquals(p.getGraph().getNodes().size(), 1);
//
//    }
//
//    @Test
//    public void testGetPath() throws Exception {
//        Diagram p = getEmptyProject();
//        assertEquals(p.getPath().toString(), new File(folder.getRoot(), "my-project").getPath());
//    }
//
//    @Test
//    public void testGetRevisionPath() throws Exception {
//        File path = new File(folder.getRoot(), "my-project");
//        Diagram p = getEmptyProject();
//        assertEquals(p.getRevisionPath(), new File(path, Models.Project.DIFF_FILE).toPath());
//    }
//
//    @Test
//    public void testGetName() throws Exception {
//        Diagram p = getEmptyProject();
//        assertEquals(p.getName(), "my-project");
//    }
//
//    @Test
//    public void testSave() throws Exception {
//        // TODO
//    }
//
//    @Test
//    public void testGetDiskTikz() throws Exception {
//        File save = folder.newFile(Models.Project.SAVE_FILE);
//        PrintWriter writer = new PrintWriter(save);
//        final String tikz = "\\node[circle, draw]() at (0,0) {test-label}";
//        writer.print(tikz);
//        writer.close();
//
//        Diagram p = new Diagram(folder.getRoot().toPath());
//        TikzGraph g = p.getGraph();
//        g.add(new TikzCircle(10));
//
//        assertEquals(tikz, p.getDiskTikz());
//        assertNotEquals(tikz, g.toString());
//    }
//
//    @Test
//    public void testGetTikzPath() throws Exception {
//        Diagram p = getEmptyProject();
//        File projectPath = new File(folder.getRoot(), "my-project");
//        assertEquals(p.getTikzPath().toString(), new File(projectPath, Models.Project.SAVE_FILE).toString());
//    }
//
//    @Test
//    public void testGetDiffPath() throws Exception {
//        Diagram p = getEmptyProject();
//        File projectPath = new File(folder.getRoot(), "my-project");
//        assertEquals(p.getDiffPath().toString(), new File(projectPath, Models.Project.DIFF_FILE).toString());
//    }
//
//    @Test
//    public void testRename() throws Exception {
//        // TODO
//    }
//
//    @Test
//    public void testGetLastChange() throws Exception {
//        Diagram p = getEmptyProject();
//        p.save();
//
//        List<Diff> diffs = new ArrayList<>();
//        Date d = new Date(1000);
//        diffs.add(new Diff(d, "diff one"));
//
//        Date d2 = new Date(2000);
//        diffs.add(new Diff(d2, "diff 2"));
//
//        p.writeDiffs(diffs);
//
//        assertEquals(d2, p.getLastChange());
//    }
//
//    @Test
//    public void testCompareToSame() throws Exception {
//        Diagram p1 = new Diagram(Paths.get("abc"));
//        Diagram p2 = new Diagram(Paths.get("abc"));
//        assertEquals(p1.compareTo(p2), 0);
//    }
//
//    @Test
//    public void testCompareToDifferent() throws Exception {
//        Diagram p1 = new Diagram(Paths.get("abc"));
//        Diagram p2 = new Diagram(Paths.get("def"));
//        assertTrue(p1.compareTo(p2) < 0);
//        assertTrue(p2.compareTo(p1) > 0);
//    }
//
//    @Test
//    public void testGetDiffs() throws Exception {
//        Diagram p = getEmptyProject();
//        p.save();
//        List<Diff> diffs = new ArrayList<>();
//        diffs.add(new Diff(new Date(0), "diff one"));
//
//        FileOutputStream fs = new FileOutputStream(p.getDiffPath().toFile());
//        ObjectOutputStream os = new ObjectOutputStream(fs);
//        os.writeObject(diffs);
//        os.close();
//        fs.close();
//
//        List<Diff> readDiffs = p.getDiffs();
//        assertEquals(readDiffs.get(0).getDate(), new Date(0));
//        assertEquals(readDiffs.get(0).getPatch(), "diff one");
//    }
//
//    @Test
//    public void testWriteDiffs() throws Exception {
//        Diagram p = getEmptyProject();
//        p.save();
//
//        List<Diff> diffs = new ArrayList<>();
//        diffs.add(new Diff(new Date(0), "diff one"));
//
//        p.writeDiffs(diffs);
//
//        FileInputStream fs = new FileInputStream(p.getDiffPath().toFile());
//        ObjectInputStream os = new ObjectInputStream(fs);
//        List<Diff> writtenDiffs = (List<Diff>) os.readObject();
//        os.close();
//        fs.close();
//
//        assertEquals(writtenDiffs.get(0).getDate(), new Date(0));
//        assertEquals(writtenDiffs.get(0).getPatch(), "diff one");
//    }


//@Test
//public void testUndoRedo() throws Exception {
//        Project p = getEmptyProject();
//        p.save();
//
//        TikzGraph graph = p.getGraph();
//        graph.add(new TikzCircle(4));
//        p.save();
//
//        Assert.assertEquals(p.getGraph().size(), 1);
//        List<Diff> listDiff = p.getDiffs();
//
//        p.undo();
//
//        Assert.assertEquals(listDiff.size()-1, p.getDiffs().size());
//        Assert.assertEquals(p.getGraph().size(), 0);
//
//        p.redo();
//        Assert.assertEquals(listDiff.size(), p.getDiffs().size());
//        Assert.assertEquals(p.getGraph().size(), 1);
//        }
//}
