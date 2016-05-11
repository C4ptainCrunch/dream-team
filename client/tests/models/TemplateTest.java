package models;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import misc.utils.ClientTest;
import models.tikz.TikzCircle;
import models.tikz.TikzGraph;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class TemplateTest extends ClientTest {

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
        Path p = Paths.get(folder.toString());
        deleteFile(TEST_1_FILENAME, p);
        deleteFile(TEST_2_FILENAME, p);
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testFileCreation() throws Exception {
        File f = template.saveTemplate(folder.newFile(TEST_1_FILENAME).toString(), folder.toString());

        assertEquals(f.getName(), template.getTemplateName());
        assertEquals(f.exists(), true);
    }

    @Test
    public void testSaveTemplate() throws Exception {
        TikzGraph graph = new TikzGraph();
        graph.add(new TikzCircle());
        template.setGraph(graph);
        Path p = Paths.get(folder.getRoot().toString());

        template.saveTemplate(folder.newFile(TEST_2_FILENAME).toString(), folder.toString());
        File f = p.resolve(TEST_2_FILENAME).toFile();
        template.loadTemplate(f);
        assertEquals(graph.size(), template.getGraph().size());
    }
}
