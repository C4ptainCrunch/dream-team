package models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import models.project.TikzIO;
import models.tikz.TikzGraph;
import constants.GUI;

/**
 * A Template object is a model representing a TikzGraph that can be saved as a template and
 * used in the current or the other projects as a droppable object.
 */

public class Template extends TikzIO {

    private File template_file;

    public Template() {
        super();
    }

    public Template(TikzGraph g) {
        super(g);
    }

    private void createDirectory() throws IOException {
        File dir = Paths.get(GUI.Template.DIR).toFile();
        dir.mkdir();
    }

    /**
     * Create the File object in which the template will be saved.
     * @param filename the filename of the File object.
     * @param dir the directory where the File will be located.
     * @return the File object created.
     * @throws IOException if an I/O error occurs.
     */

    public static File createSaveFile(String filename, String dir) throws IOException {
        File file = TikzIO.createSaveFile(filename, dir);
        return file;
    }

    /**
     * I/O operation that saves the Template object in a file.
     * @param name the name of the template.
     * @param dir the directory where the Template will be saved.
     * @return the File object in which the template has been saved.
     * @throws IOException if an I/O error occurs.
     */

    public File saveTemplate(String name, String dir) throws IOException {
        createDirectory();
        if (graph != null && name != null) {
            template_file = createSaveFile(name, dir);
            writeTikz(this.graph.toString(), template_file);
        }
        return template_file;
    }

    /**
     * This method overloads the previous one.
     * @param name the name of the template.
     * @return the File object in which the template has been saved.
     * @throws IOException if an I/O error occurs.
     */

    public File saveTemplate(String name) throws IOException {
        return saveTemplate(name, GUI.Template.DIR);
    }

    /**
     * Load a template in the current project.
     * @param file The File object where the template is located.
     * @throws IOException if an I/O error occurs.
     */

    public void loadTemplate(File file) throws IOException {
        Path p = Paths.get(file.getAbsolutePath());
        template_file = file;
        graph = new TikzGraph(p);
    }

    /**
     * Get the name of the template.
     * @return the template's name.
     */

    public String getTemplateName() {
        return template_file.getName();
    }
}
