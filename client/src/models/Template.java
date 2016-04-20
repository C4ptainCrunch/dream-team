package models;

import constants.GUI;
import models.tikz.TikzGraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

// TODO: Refactor this, duplicated code with Project model.

public class Template {

    private TikzGraph graph;
    private File template_file;

    public Template(){
        graph = null;
    }

    public Template(TikzGraph g){
        graph = g;
    }

    private void createDirectory() throws IOException{
        File dir = Paths.get(GUI.Template.DIR).toFile();
        dir.mkdir();
    }

    private void createSaveFile(String filename) throws IOException{
        Path dir = Paths.get(GUI.Template.DIR);
        template_file = dir.resolve(filename).toFile();
        if (!template_file.exists()){
            new FileOutputStream(template_file).close();
        }
    }

    public void saveTemplate(File file) throws IOException{
        createDirectory();
        if (graph != null && file != null){
            createSaveFile(file.getName());
            PrintWriter sourceWriter = new PrintWriter(template_file);
            sourceWriter.println(this.graph.toString());
            sourceWriter.close();
        }
    }

    public void loadTemplate(File file) throws IOException{
        Path p = Paths.get(file.getAbsolutePath());
        template_file = file;
        graph = new TikzGraph(p.toString());
    }

    public TikzGraph getTemplateGraph(){
        return graph;
    }

    public String getTemplateName(){
        return template_file.getName();
    }
}
