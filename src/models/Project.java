package models;

import models.tikz.RecentProjects;
import models.tikz.TikzGraph;
import utils.SaverUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Project {
    private String path;
    private TikzGraph graph;

    public Project(String path, TikzGraph graph) {
        this.path = path;
        this.graph = graph;
    }

    public static Project fromPath(String path) throws IOException {
        TikzGraph graph = new TikzGraph(Paths.get(path, constants.Models.Project.SAVE_FILE).toString());
        Project p = new Project(path, graph);
        return p;
    }

    public static void initialize(File dir) throws IOException {
        dir.mkdir();
        Path path = dir.toPath();
        new FileOutputStream(path.resolve(constants.Models.Project.SAVE_FILE).toFile()).close();
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public TikzGraph getGraph() {
        return graph;
    }

    public String getPath() {
        return path;
    }

    public Path getRevisionPath() {
        return Paths.get(this.getPath(), constants.Models.Project.DIFF_FILE);
    }

    public String getName() {
        return this.path;
    }

    public void save() throws IOException {
        SaverUtil.writeToFile(this.getDiskTikz(), this.graph.toString(), this.path);
        RecentProjects.addProject(this);
    }

    public String getDiskTikz() throws IOException {
        return new String(Files.readAllBytes(Paths.get(this.getTikzPath())));
    }

    public String getTikzPath() {
        return this.path + "/" + constants.Models.Project.SAVE_FILE; // TODO : be windows compliant
    }

    public Path getDiffPath() {
        return Paths.get(this.path + "/" + constants.Models.Project.DIFF_FILE);
    }

    public void rename(Path newPath) throws IOException {
        RecentProjects.addProject(this);
    }

    // TODO : André : refactor this asap
    public String getLastRevision() {
        String ch = "";

        try {
            ArrayList tmp = new ArrayList<String>();
            FileReader fr = new FileReader(this.getRevisionPath().toFile());
            BufferedReader br = new BufferedReader(fr);

            do {
                try {
                    ch = br.readLine();
                } catch (IOException e) {
                    e.getStackTrace();
                }
                tmp.add(ch);
            } while (!ch.startsWith("2016"));
        } catch (FileNotFoundException e) {
            e.getStackTrace();
        }
        return ch;
    }
}
