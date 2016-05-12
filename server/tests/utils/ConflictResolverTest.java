package utils;

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
    File oneConflictOneSimpleAdd;

    @Before
    public void setUp() throws Exception {
        baseFile = new File("tests/utils/diffsFiles/base.diff");
        localFile = new File("tests/utils/diffsFiles/local.diff");
        conflictFile = new File("tests/utils/diffsFiles/conflict.diff");
        oneConflictOneSimpleAdd = new File("tests/utils/diffsFiles/oneConflictOneSimpleAdd.diff");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testcheckHasConflict() throws Exception {
        conflictResolver.update(localFile, baseFile);
        assertEquals(conflictResolver.checkHasConflict(), new Integer(0));

        conflictResolver.update(localFile, conflictFile);
        assertEquals(conflictResolver.checkHasConflict(), new Integer(2));

        conflictResolver.update(localFile, oneConflictOneSimpleAdd);
        assertEquals(conflictResolver.checkHasConflict(), new Integer(1));
    }
}