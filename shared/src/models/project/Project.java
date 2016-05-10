package models.project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import models.tikz.TikzGraph;
import utils.DiffUtil;
import utils.Log;
import utils.RecentProjects;

/**
 * This class represents a single project created by a user. A project consists
 * of a tikz graph and a file path containing the specific files (save, diffs
 * ..) for this project
 */
public class Project extends TikzIO implements Comparable<Project> {
    private final static Logger logger = Log.getLogger(Project.class);
    private Path path;
    private boolean isTempDir = false;



    /**
     * Constructs a new Project with a given file path. The project must have
     * been already created in order to use this constructor.
     *
     * @param path
     *            The file path
     * @throws IOException
     */
    public Project(Path path) {
        super();
        this.path = path;
        try {
            this.graph = new TikzGraph(this.getTikzPath().toString());
        } catch (IOException e) {
            logger.fine("Warning: error while opening the project's tikz file: " + e.toString());
            this.graph = new TikzGraph();
        }
    }

    public Project() throws IOException {
        super();
        this.graph = new TikzGraph();
        this.path = Files.createTempDirectory(null);
        this.isTempDir = true;
    }

    /**
     * Transforms the project into a string (ie. the path of the project)
     *
     * @return The name of the poject (ie. the path)
     */
    @Override
    public String toString() {
        return this.getName();
    }

    /**
     * Getter for the path of this project
     *
     * @return The path
     */
    public Path getPath() {
        return path;
    }

    /**
     * Getter for the path of the modifications file of this project
     *
     * @return The path of the modification file
     */
    public Path getRevisionPath() {
        return this.getPath().resolve(constants.Models.Project.DIFF_FILE);
    }

    /**
     * Getter for the name of this project (ie. the path)
     *
     * @return The name
     */
    public String getName() {
        return this.path.getFileName().toString();
    }

    /**
     * Saves the project to disk. It computes the diff between the actual
     * version and the last one, writes the new history to disk and then writes
     * the source to disk. It also adds the project to the recent projects list.
     *
     * @throws IOException
     *             when writing to the disk failed
     * @throws ClassNotFoundException
     *             when the diff file is corrupted
     */
    public void save() throws IOException, ClassNotFoundException {
        if(!Files.exists(this.path)){
            this.path.toFile().mkdirs();
        }

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
        super.writeTikz(this.graph.toString(), this.getTikzPath());
        RecentProjects.addProject(this);
    }

    /**
     * Getter for the tikz text contained in the save file of this project.
     *
     * @return The current saved tikz text
     * @throws IOException
     */
    public String getDiskTikz() throws IOException {
        return new String(Files.readAllBytes(this.getTikzPath()));
    }

    /**
     * Getter for the path of the save file for this project.
     *
     * @return The path
     */
    public Path getTikzPath() {
        return this.path.resolve(constants.Models.Project.SAVE_FILE);
    }

    /**
     * Getter for the path of the modifications file for this project.
     *
     * @return The path
     */
    public Path getDiffPath() {
        return Paths.get(this.path + "/" + constants.Models.Project.DIFF_FILE);
    }

    /**
     * Renames the project with a given file
     *
     * @param newDir
     *            The new file to be renamed with
     * @throws IOException
     */
    public void rename(File newDir) throws IOException {
        File original = this.getPath().toFile();
        original.renameTo(newDir);
        this.path = newDir.toPath();
        this.isTempDir = false;
        RecentProjects.addProject(this);

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Gets the list of diffs from the diff file.
     *
     * @return A list of Diff objects
     * @throws IOException
     *             : when reading the file failed
     * @throws ClassNotFoundException
     *             : when the file is corrupted
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
     *
     * @param diffs
     * @throws IOException
     *             when writing to the file failed
     */
    public void writeDiffs(List<Diff> diffs) throws IOException {
        FileOutputStream fs = new FileOutputStream(this.getDiffPath().toFile());
        ObjectOutputStream os = new ObjectOutputStream(fs);
        os.writeObject(diffs);

        os.close();
        fs.close();
    }

    /**
     * Compares this Project with the given Project for order (uses the path for
     * comparison).
     *
     * @param other
     *            The project to be compared with
     * @return The order
     */
    @Override
    public int compareTo(Project other) {
        return this.getPath().compareTo(other.getPath());
    }

    /**
     * Gets the date of the last change of the project
     *
     * @return Date the date
     * @throws IOException
     *             when the diff file does not exist
     * @throws ClassNotFoundException
     *             when the diff file is corrupted
     */
    public Date getLastChange() throws IOException, ClassNotFoundException {
        List<Diff> diffs = this.getDiffs();
        if (diffs.size() == 0) {
            return null;
        }
        return diffs.get(diffs.size() - 1).getDate();
    }

    public boolean exists() {
        return Files.exists(this.path);
    }

    public boolean isTemporary() {
        return isTempDir;
    }
}
