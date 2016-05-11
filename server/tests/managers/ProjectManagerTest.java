package managers;

import models.project.Diff;
import org.junit.Before;
import org.junit.Test;
import utils.ConflictResolver;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class ProjectManagerTest {
    ProjectManager projectManager;
    File baseFile;
    File localFile;
    File conflictFile;

    @Before
    public void setUp() throws Exception {
        baseFile = new File("tests/utils/diffsFiles/base.diff");
        localFile = new File("tests/utils/diffsFiles/local.diff");
        conflictFile = new File("tests/utils/diffsFiles/conflict.diff");
        projectManager = new ProjectManager();
    }

    @Test
    public void testCreateTikzFromDiffs() throws Exception {
        ConflictResolver conflictResolver = projectManager.getConflictResolver();
        conflictResolver.update(baseFile,localFile,conflictFile);
        List<Diff> resolvedDiff = conflictResolver.resolve("saveUserVersionOnly");
        String tikzGraph = projectManager.createTikzFromDiffs(resolvedDiff);
        assertEquals(tikzGraph,"\\node[circle, draw, radius=50](10b7aa06-3811-49d3-96fd-6f7c8b891d86) at (0.794,4.710){};\n" +
                "\\node[regular polygon, draw, minimum size=50](473f94bf-bb60-4cc8-9b74-af5aed97f5fe) at (-0.423,-0.370){};\n");
    }
}