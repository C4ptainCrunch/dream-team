package models;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.project.Diff;
import models.project.Project;
import models.tikz.TikzCircle;
import models.tikz.TikzGraph;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import constants.Models;

public class ProjectTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private Project getEmptyProject() throws IOException {
        File path = new File(folder.getRoot(), "my-project");
        Project.initialize(path);

        return Project.fromPath(path.toString());
    }

    @Test
    public void testFromPath() throws Exception {
        File save = folder.newFile(Models.Project.SAVE_FILE);
        folder.newFile(Models.Project.DIFF_FILE);
        String path = save.getParent();

        Project p = Project.fromPath(path);
        assertEquals(p.getPath().toString(), path);
    }

    @Test
    public void testInitialize() throws Exception {
        File path = new File(folder.getRoot(), "my-project");
        assertFalse(path.exists());

        Project.initialize(path);

        assertTrue(path.exists());
        assertTrue(new File(path, Models.Project.SAVE_FILE).exists());
    }

    @Test
    public void testToString() throws Exception {
        Project p = getEmptyProject();
        assertEquals(p.toString(), "my-project");
    }

    @Test
    public void testGetGraph() throws Exception {
        File save = folder.newFile(Models.Project.SAVE_FILE);
        PrintWriter writer = new PrintWriter(save);
        writer.print("\\node[circle, draw]() at (0,0) {test-label}");
        writer.close();

        Project p = Project.fromPath(folder.getRoot().toString());

        assertEquals(p.getGraph().getNodes().size(), 1);

    }

    @Test
    public void testGetPath() throws Exception {
        Project p = getEmptyProject();
        assertEquals(p.getPath().toString(), new File(folder.getRoot(), "my-project").getPath());
    }

    @Test
    public void testGetRevisionPath() throws Exception {
        File path = new File(folder.getRoot(), "my-project");
        Project p = getEmptyProject();
        assertEquals(p.getRevisionPath(), new File(path, Models.Project.DIFF_FILE).toPath());
    }

    @Test
    public void testGetName() throws Exception {
        Project p = getEmptyProject();
        assertEquals(p.getName(), "my-project");
    }

    @Test
    public void testSave() throws Exception {
        // TODO
    }

    @Test
    public void testGetDiskTikz() throws Exception {
        File save = folder.newFile(Models.Project.SAVE_FILE);
        PrintWriter writer = new PrintWriter(save);
        final String tikz = "\\node[circle, draw]() at (0,0) {test-label}";
        writer.print(tikz);
        writer.close();

        Project p = Project.fromPath(folder.getRoot().toString());
        TikzGraph g = p.getGraph();
        g.add(new TikzCircle(10));

        assertEquals(tikz, p.getDiskTikz());
        assertNotEquals(tikz, g.toString());
    }

    @Test
    public void testGetTikzPath() throws Exception {
        Project p = getEmptyProject();
        File projectPath = new File(folder.getRoot(), "my-project");
        assertEquals(p.getTikzPath().toString(), new File(projectPath, Models.Project.SAVE_FILE).toString());
    }

    @Test
    public void testGetDiffPath() throws Exception {
        Project p = getEmptyProject();
        File projectPath = new File(folder.getRoot(), "my-project");
        assertEquals(p.getDiffPath().toString(), new File(projectPath, Models.Project.DIFF_FILE).toString());
    }

    @Test
    public void testRename() throws Exception {
        // TODO
    }

    @Test
    public void testGetLastChange() throws Exception {
        Project p = getEmptyProject();
        List<Diff> diffs = new ArrayList<>();
        Date d = new Date(1000);
        diffs.add(new Diff(d, "diff one"));

        Date d2 = new Date(2000);
        diffs.add(new Diff(d2, "diff 2"));

        p.writeDiffs(diffs);

        assertEquals(d2, p.getLastChange());
    }

    @Test
    public void testCompareToSame() throws Exception {
        Project p1 = new Project("abc", new TikzGraph());
        Project p2 = new Project("abc", new TikzGraph());
        assertEquals(p1.compareTo(p2), 0);
    }

    @Test
    public void testCompareToDifferent() throws Exception {
        Project p1 = new Project("abc", new TikzGraph());
        Project p2 = new Project("def", new TikzGraph());
        assertTrue(p1.compareTo(p2) < 0);
        assertTrue(p2.compareTo(p1) > 0);
    }

    @Test
    public void testGetDiffs() throws Exception {
        Project p = getEmptyProject();
        List<Diff> diffs = new ArrayList<>();
        diffs.add(new Diff(new Date(0), "diff one"));

        FileOutputStream fs = new FileOutputStream(p.getDiffPath().toFile());
        ObjectOutputStream os = new ObjectOutputStream(fs);
        os.writeObject(diffs);
        os.close();
        fs.close();

        List<Diff> readDiffs = p.getDiffs();
        assertEquals(readDiffs.get(0).getDate(), new Date(0));
        assertEquals(readDiffs.get(0).getPatch(), "diff one");
    }

    @Test
    public void testWriteDiffs() throws Exception {
        Project p = getEmptyProject();
        List<Diff> diffs = new ArrayList<>();
        diffs.add(new Diff(new Date(0), "diff one"));

        p.writeDiffs(diffs);

        FileInputStream fs = new FileInputStream(p.getDiffPath().toFile());
        ObjectInputStream os = new ObjectInputStream(fs);
        List<Diff> writtenDiffs = (List<Diff>) os.readObject();
        os.close();
        fs.close();

        assertEquals(writtenDiffs.get(0).getDate(), new Date(0));
        assertEquals(writtenDiffs.get(0).getPatch(), "diff one");
    }
}
