package models;

import models.tikz.TikzGraph;
import utils.SaverUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Project {
    private String path;
    private TikzGraph graph;

    public Project(String path, TikzGraph graph) {
        this.path = path;
        this.graph = graph;
    }

    public static Project fromPath(String path) throws IOException {
        TikzGraph graph = new TikzGraph(path);
        Project p = new Project(path, graph);
        return p;
    }

    public TikzGraph getGraph() {
        return graph;
    }

    public String getPath() {
        return path;
    }

    public void save() throws IOException {
        SaverUtil.writeToFile(this.getDiskTikz(), this.graph.toString(), this.path);
    }

    public String getDiskTikz() throws IOException {
        return new String(Files.readAllBytes(Paths.get(this.getTikzPath())));
    }

    public String getTikzPath() {
        return this.path + "/tikz.save"; // TODO : be windows compliant
    }
}
