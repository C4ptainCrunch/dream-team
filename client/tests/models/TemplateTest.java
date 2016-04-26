package models;

import static org.junit.Assert.*;

import constants.GUI;
import models.tikz.TikzCircle;
import models.tikz.TikzGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by aurelien on 26/04/16.
 */
public class TemplateTest {

    private static final String TEST_1_FILENAME = "test";
    private static final String TEST_2_FILENAME = "test2";

    private Template template;

    private void deleteFile(String filename, Path p) throws Exception{
        File f = p.resolve(filename).toFile();
        if (f != null){
            f.delete();
        }
    }

    @Before
    public void setUp() throws Exception{
        template = new Template(new TikzGraph());
    }

    @After
    public void tearDown() throws Exception{
        Path p = Paths.get(GUI.Template.DIR);
        deleteFile(TEST_1_FILENAME, p);
        deleteFile(TEST_2_FILENAME, p);
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testFileCreation() throws Exception {
        File f = template.saveTemplate(TEST_1_FILENAME);

        assertEquals(f.getName(), template.getTemplateName());
        assertEquals(f.exists(), true);
    }

    @Test
    public void testSaveTemplate() throws Exception {
        TikzGraph graph = new TikzGraph();
        graph.add(new TikzCircle());
        template.setGraph(graph);
        Path p = Paths.get(GUI.Template.DIR);

        template.saveTemplate(TEST_2_FILENAME);
        File f = p.resolve(TEST_2_FILENAME).toFile();
        template.loadTemplate(f);
        assertEquals(graph.size(), template.getGraph().size());
    }
}