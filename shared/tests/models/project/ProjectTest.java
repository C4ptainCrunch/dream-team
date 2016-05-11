package models.project;

import static org.junit.Assert.assertEquals;

import utils.SharedTest;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.nio.file.Path;
import java.nio.file.Paths;


public class ProjectTest extends SharedTest{

    private Project project;
    private Diagram diagram;

    @Rule
    TemporaryFolder tmpFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception{
        Path p = Paths.get(tmpFolder.getRoot().getPath());
    }

    @After
    public void tearDown() throws Exception{

    }

    @Test
    public void testWriteSource() throws Exception{

    }

    @Test
    public void testWriteDiff() throws Exception{

    }
