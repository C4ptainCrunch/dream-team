package models;

import models.tikz.TikzGraph;
import utils.SaverUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This class represents a single project created by a user.
 * A project consists of a tikz graph and a file path containing
 * the specific files (save, diffs ..) for this project
 */
public class Project implements Comparable<Project>{
    private String path;
    private TikzGraph graph;

    /**
     * Constructs a new Project with a given
     * file path and tikz graph
     * @param path The file path
     * @param graph the tikz graph
     */
    public Project(String path, TikzGraph graph) {
        this.path = path;
        this.graph = graph;
    }

    /**
     * Constructs a new Project with a given file path.
     * The project must have been already created
     * in order to use this constructor.
     * @param path The file path
     * @return the project imported from the file path
     * @throws IOException
     */
    public static Project fromPath(String path) throws IOException {
        Project p = new Project(path, null);
        TikzGraph graph = new TikzGraph(p.getTikzPath().toString());
        p.setGraph(graph);
        return p;
    }

    /**
     * Initializes the files for this project
     * with a given directory
     * @param dir The directory where the files are created
     * @throws IOException
     */
    public static void initialize(File dir) throws IOException {
        dir.mkdir();
        Path path = dir.toPath();
        File saveFile = path.resolve(constants.Models.Project.SAVE_FILE).toFile();
        if(!saveFile.exists()){
            new FileOutputStream(saveFile).close();
        }
    }

    /**
     * Transforms the project into a string (ie. the path of the project)
     * @return The name of the poject (ie. the path)
     */
    @Override
    public String toString() {
        return this.getName();
    }

    /**
     * Getter for the tikz graph of this project
     * @return the tikz graph
     */
    public TikzGraph getGraph() {
        return graph;
    }

    /**
     * Getter for the path of this project
     * @return The path
     */
    public String getPath() {
        return path;
    }

    /**
     * Getter for the path of the modifications file of this project
     * @return The path of the modification file
     */
    public Path getRevisionPath() {
        return Paths.get(this.getPath(), constants.Models.Project.DIFF_FILE);
    }

    /**
     * Getter for the name of this project (ie. the path)
     * @return The name
     */
    public String getName() {
        return this.path;
    }

    /**
     * Appends the modifications to the modifications
     * file of this project by comparing the actual
     * saved tikz text and the new one.
     * @throws IOException
     */
    public void save() throws IOException {
        SaverUtil.writeDiffToFile(this.getDiskTikz(), this.graph.toString(), this.path);

        FileWriter f = new FileWriter(this.getTikzPath().toFile());
        BufferedWriter bufferedWriter = new BufferedWriter(f);
        bufferedWriter.append(this.graph.toString());
        bufferedWriter.close();

        RecentProjects.addProject(this);
    }

    /**
     * Getter for the tikz text contained in the save file
     * of this project.
     * @return The current saved tikz text
     * @throws IOException
     */
    public String getDiskTikz() throws IOException {
        return new String(Files.readAllBytes(this.getTikzPath()));
    }

    /**
     * Getter for the path of the save file for
     * this project.
     * @return The path
     */
    public Path getTikzPath() {
        return Paths.get(this.path, constants.Models.Project.SAVE_FILE); // TODO : be windows compliant
    }

    /**
     * Getter for the path of the modifications
     * file for this project.
     * @return The path
     */
    public Path getDiffPath() {
        return Paths.get(this.path + "/" + constants.Models.Project.DIFF_FILE);
    }

    /**
     * Renames the project with a given file
     * @param newDir The new file to be renamed with
     * @throws IOException
     */
    public void rename(File newDir) throws IOException {
        File original = new File(this.getPath());
        original.renameTo(newDir);
        this.path = newDir.toPath().toString();
        RecentProjects.addProject(this);
    }

    // TODO : André : refactor this asap

    /**
     * Getter for the last revision of this project
     * @return the last revision
     */
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

    /**
     * Setter for the tikz graph linked to this project
     * @param graph The tikz graph
     */
    public void setGraph(TikzGraph graph) {
        this.graph = graph;
    }

    /**
     * Compares this Project with the given Project for order.
     * @param other The project to be compared with
     * @return The order
     */
    @Override
    public int compareTo(Project other) {
        return this.getPath().compareTo(other.getPath());
    }
}
