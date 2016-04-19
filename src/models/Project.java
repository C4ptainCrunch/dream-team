package models;

import constants.Models;
import models.tikz.TikzGraph;
import utils.DiffUtil;
import utils.Log;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

public class Project implements Comparable<Project>{
    private Path path;
    private TikzGraph graph;
    private final static Logger logger = Log.getLogger(Project.class);

    public Project(String path, TikzGraph graph) {
        this.path = Paths.get(path);
        this.graph = graph;
    }

    public static Project fromPath(String path) throws IOException {
        Project p = new Project(path, null);
        TikzGraph graph = new TikzGraph(p.getTikzPath().toString());
        p.setGraph(graph);
        return p;
    }

    public static void initialize(File dir) throws IOException {
        dir.mkdir();
        Path path = dir.toPath();
        File saveFile = path.resolve(constants.Models.Project.SAVE_FILE).toFile();
        if(!saveFile.exists()){
            new FileOutputStream(saveFile).close();
        }

        File diffFile = path.resolve(Models.Project.DIFF_FILE).toFile();
        if(!diffFile.exists()){
            new FileOutputStream(diffFile).close();
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public TikzGraph getGraph() {
        return graph;
    }

    public Path getPath() {
        return path;
    }

    public Path getRevisionPath() {
        return this.getPath().resolve(constants.Models.Project.DIFF_FILE);
    }

    public String getName() {
        return this.path.getFileName().toString();
    }

    public void save()  throws IOException, ClassNotFoundException{
        List<Diff> diffs = null;
        try {
            diffs = this.getDiffs();
        } catch (IOException | ClassNotFoundException e) {
            logger.fine("Warning while reading the diff file : " + e.toString());
            diffs = new ArrayList<>();
        }

        String originalTikz = "";
        try {
            originalTikz = this.getDiskTikz();
        } catch (IOException e) {
            logger.fine("Warning while reading the save file : " + e.toString());
        }

        String diffString = DiffUtil.diff(originalTikz, this.graph.toString());
        diffs.add(new Diff(new Date(), diffString));

        this.writeDiffs(diffs);
        this.writeTikz(this.graph.toString());
        RecentProjects.addProject(this);
    }

    public String getDiskTikz() throws IOException {
        return new String(Files.readAllBytes(this.getTikzPath()));
    }

    public Path getTikzPath() {
        return this.path.resolve(constants.Models.Project.SAVE_FILE);
    }

    public Path getDiffPath() {
        return Paths.get(this.path + "/" + constants.Models.Project.DIFF_FILE);
    }

    public void rename(File newDir) throws IOException {
        File original = this.getPath().toFile();
        original.renameTo(newDir);
        this.path = newDir.toPath();
        RecentProjects.addProject(this);
    }

    public List<Diff> getDiffs() throws IOException, ClassNotFoundException {
        FileInputStream fs = new FileInputStream(this.getDiffPath().toFile());
        ObjectInputStream os = new ObjectInputStream(fs);
        List<Diff> diffs = (List<Diff>) os.readObject();
        os.close();
        fs.close();

        return diffs;
    }

    public void writeDiffs(List<Diff> diffs) throws IOException {
        FileOutputStream fs = new FileOutputStream(this.getDiffPath().toFile());
        ObjectOutputStream os = new ObjectOutputStream(fs);
        os.writeObject(diffs);

        os.close();
        fs.close();
    }

    public void writeTikz(String tikz) throws IOException {
        PrintWriter sourceWriter = new PrintWriter(this.getTikzPath().toFile());
        sourceWriter.println(tikz);
        sourceWriter.close();
    }

    public void setGraph(TikzGraph graph) {
        this.graph = graph;
    }

    @Override
    public int compareTo(Project other) {
        return this.getPath().compareTo(other.getPath());
    }

    public Date getLastChange() throws IOException, ClassNotFoundException {
        List<Diff> diffs = this.getDiffs();
        if(diffs.size() == 0){
            return null;
        }
        return diffs.get(diffs.size() - 1).getDate();
    }
}
