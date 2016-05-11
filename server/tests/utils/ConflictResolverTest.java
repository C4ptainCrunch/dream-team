package utils;

import models.project.Diff;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;


import static org.junit.Assert.*;


public class ConflictResolverTest {
    ConflictResolver conflictResolver = new ConflictResolver();
    File baseFile;
    File localFile;
    File conflictFile;

    @Before
    public void setUp() throws Exception {
        baseFile = new File("tests/utils/diffsFiles/base.diff");
        localFile = new File("tests/utils/diffsFiles/local.diff");
        conflictFile = new File("tests/utils/diffsFiles/conflict.diff");

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testcheckHasConflict() throws Exception {
        conflictResolver.update(baseFile, localFile, baseFile);
        assertEquals(conflictResolver.checkHasConflict(), new Integer(0));

        conflictResolver.update(baseFile, localFile, conflictFile);
        assertEquals(conflictResolver.checkHasConflict(), new Integer(1));

        conflictResolver.update(baseFile, baseFile, conflictFile);
        assertEquals(conflictResolver.checkHasConflict(), new Integer(2));
    }
}