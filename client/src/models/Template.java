package models;

import models.tikz.TikzGraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by aurelien on 20/04/16.
 */
public class Template {

    private static final String DIR = "./assets/templates/";

    private TikzGraph graph;
    private File template_file;

    public Template(TikzGraph g){
        graph = g;
    }

    private void createDirectory() throws IOException{
        File dir = Paths.get(DIR).toFile();
        dir.mkdir();
    }

    private void createSaveFile(String filename) throws IOException{
        Path dir = Paths.get(DIR);
        template_file = dir.resolve(filename).toFile();
        if (!template_file.exists()){
            new FileOutputStream(template_file).close();
        }
    }

    public void saveTemplate(File file) throws IOException{
        createDirectory();
        if (graph != null){
            createSaveFile(file.getName());
            PrintWriter sourceWriter = new PrintWriter(template_file);
            sourceWriter.println(this.graph.toString());
            sourceWriter.close();
        }
    }

    public TikzGraph loadTemplate(File path){
        return null;
    }
}
