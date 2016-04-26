package models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import models.project.TikzIO;
import models.tikz.TikzGraph;
import constants.GUI;

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

    private void createSaveFile(String filename) throws IOException {
        template_file = TikzIO.createSaveFile(filename, GUI.Template.DIR);
    }

    public File saveTemplate(String name) throws IOException {
        createDirectory();
        if (graph != null && name != null) {
            createSaveFile(name);
            writeTikz(this.graph.toString(), template_file);
        }
        return template_file;
    }

    public void loadTemplate(File file) throws IOException {
        Path p = Paths.get(file.getAbsolutePath());
        template_file = file;
        graph = new TikzGraph(p.toString());
    }

    public String getTemplateName() {
        return template_file.getName();
    }
}
