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

/**
 * This class represents a single project created by a user.
 * A project consists of a tikz graph and a file path containing
 * the specific files (save, diffs ..) for this project
 */
public class Project implements Comparable<Project>{
    private Path path;
    private TikzGraph graph;
    private final static Logger logger = Log.getLogger(Project.class);

    /**
     * Constructs a new Project with a given
     * file path and tikz graph
     * @param path The file path
     * @param graph the tikz graph
     */
    public Project(String path, TikzGraph graph) {
        this.path = Paths.get(path);
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

        File diffFile = path.resolve(Models.Project.DIFF_FILE).toFile();
        if(!diffFile.exists()){
            new FileOutputStream(diffFile).close();
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
    public Path getPath() {
        return path;
    }

    /**
     * Getter for the path of the modifications file of this project
     * @return The path of the modification file
     */
    public Path getRevisionPath() {
        return this.getPath().resolve(constants.Models.Project.DIFF_FILE);
    }

    /**
     * Getter for the name of this project (ie. the path)
     * @return The name
     */
    public String getName() {
        return this.path.getFileName().toString();
    }

    /**
     * Saves the project to disk.
     * It computes the diff between the actual version and the last one,
     * writes the new history to disk and then writes the source to disk.
     * It also adds the project to the recent projects list.
     * @throws IOException when writing to the disk failed
     * @throws ClassNotFoundException when the diff file is corrupted
     */
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
        return this.path.resolve(constants.Models.Project.SAVE_FILE);
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
        File original = this.getPath().toFile();
        original.renameTo(newDir);
        this.path = newDir.toPath();
        RecentProjects.addProject(this);
    }

    /**
     * Gets the list of diffs from the diff file.
     * @return A list of Diff objects
     * @throws IOException : when reading the file failed
     * @throws ClassNotFoundException : when the file is corrupted
     */
    public List<Diff> getDiffs() throws IOException, ClassNotFoundException {
        FileInputStream fs = new FileInputStream(this.getDiffPath().toFile());
        ObjectInputStream os = new ObjectInputStream(fs);
        List<Diff> diffs = (List<Diff>) os.readObject();
        os.close();
        fs.close();

        return diffs;
    }

    /**
     * Serializes the diffs to the diff file
     * @param diffs
     * @throws IOException when writing to the file failed
     */
    public void writeDiffs(List<Diff> diffs) throws IOException {
        FileOutputStream fs = new FileOutputStream(this.getDiffPath().toFile());
        ObjectOutputStream os = new ObjectOutputStream(fs);
        os.writeObject(diffs);

        os.close();
        fs.close();
    }

    /**
     * Writes the tikz source of the project to the save file
     * @param  tikz : the tikz source
     * @throws IOException when writing failed
     */
    public void writeTikz(String tikz) throws IOException {
        PrintWriter sourceWriter = new PrintWriter(this.getTikzPath().toFile());
        sourceWriter.println(tikz);
        sourceWriter.close();
    }

    /**
     * Setter for the tikz graph linked to this project
     * @param graph The tikz graph
     */
    public void setGraph(TikzGraph graph) {
        this.graph = graph;
    }

    /**
     * Compares this Project with the given Project for order
     * (uses the path for comparison).
     * @param other The project to be compared with
     * @return The order
     */
    @Override
    public int compareTo(Project other) {
        return this.getPath().compareTo(other.getPath());
    }


    /**
     * Gets the date of the last change of the project
     * @return Date the date
     * @throws IOException when the diff file does not exist
     * @throws ClassNotFoundException when the diff file is corrupted
     */
    public Date getLastChange() throws IOException, ClassNotFoundException {
        List<Diff> diffs = this.getDiffs();
        if(diffs.size() == 0){
            return null;
        }
        return diffs.get(diffs.size() - 1).getDate();
    }
}
