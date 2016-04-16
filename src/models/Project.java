package models;

import models.tikz.TikzGraph;
import utils.Dirs;
import utils.SaverUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

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

    public Path getDiffPath() {
        return Paths.get(this.path + "/diffs");
    }


    public static Vector<Project> getRecentProjects() throws IOException {
        Vector<Project> projects = new Vector<>();

        Path rectentProjectsPath = Dirs.getDataDir().relativize(Paths.get("recent.paths"));

        FileReader fileReader = new FileReader(rectentProjectsPath.toFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            try {
                projects.add(Project.fromPath(line));
            } catch (IOException e) {
                // If there is an error while loading the project
                // we can safely ignore it
                // TODO : log the error (logger.debug)
            }
        }
        bufferedReader.close();

        return projects;
    }
}
