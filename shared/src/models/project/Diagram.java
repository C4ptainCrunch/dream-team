package models.project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;

import models.tikz.TikzGraph;
import utils.DiffUtil;
import utils.Log;

/**
 * This class represents a single project created by a user. A project consists
 * of a tikz graph and a file path containing the specific files (save, diffs
 * ..) for this project
 */
public class Diagram extends Observable{
    private final static Logger logger = Log.getLogger(Diagram.class);
    private String name;
    private Project project;
    private TikzGraph graph;


    public Diagram(String name, Project project) {
        this.name = name;
        this.project = project;
        try {
            this.graph = new TikzGraph(this.getSourcePath());
        } catch (IOException e) {
            logger.fine("Warning: new graph created because the original file was not found");
            this.graph = new TikzGraph();
        }
    }

    public Path getSourcePath() {
        return this.project.getDiagramSource(this.name);
    }

    public Path getDiffPath() {
        return this.project.getDiagramDiff(this.name);
    }

    public TikzGraph getGraph() {
        return this.graph;
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
     * Getter for the name of this diagram
     * @return The name
     */
    public String getName() {
        return this.name;
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

        System.out.println("save done");
    }

    /**
     * Getter for the tikz text contained in the save file of this project.
     *
     * @return The current saved tikz text
     * @throws IOException
     */
    public String getDiskTikz() throws IOException {
        return new String(Files.readAllBytes(this.getSourcePath()));
    }


    public void rename(String newName) throws IOException {
        this.project.renameDiagram(this.name, newName);
        this.name = newName;
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
        byte[] bytes = Files.readAllBytes(this.getDiffPath());
        ByteArrayInputStream bs = new ByteArrayInputStream(bytes);
        ObjectInputStream os = new ObjectInputStream(bs);
        List<Diff> diffs = (List<Diff>) os.readObject();
        os.close();
        bs.close();

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
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bs);
        os.writeObject(diffs);

        Files.write(this.getDiffPath(), bs.toByteArray());

        os.close();
        bs.close();
    }

    public void writeTikz(String tikz) throws IOException {
        Files.write(this.getSourcePath(), tikz.getBytes());
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

    public Project getProject() {
        return project;
    }

    public boolean isTemporary() {
        return this.project.isTemporary();
    }
}
