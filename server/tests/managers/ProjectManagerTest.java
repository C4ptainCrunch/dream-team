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
        System.out.println(resolvedDiff.size());
        String tikzGraph = projectManager.createTikzFromDiffs(resolvedDiff.get(0));
        System.out.println("Ok");
        System.out.println(tikzGraph);
    }
}