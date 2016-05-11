package models.project;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import models.tikz.TikzGraph;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import parser.NodeParser;
import utils.DiffUtil;
import utils.Log;
import utils.RecentProjects;

/**
 * This class represents a single project created by a user. A project consists
 * of a tikz graph and a file path containing the specific files (save, diffs
 * ..) for this project
 */
public class Diagram{
    private final static Logger logger = Log.getLogger(Diagram.class);
    private String name;
    private Project project;
    private TikzGraph graph;
    private List<Diff> redoList = new ArrayList<>();
    private boolean undoRedoFlag = false;


    public Diagram(String name, Project project) {
        this.name = name;
        this.project = project;
        try {
            this.graph = new TikzGraph(this.getSource());
        } catch (IOException e) {
            logger.fine("Warning: new graph created because the original file was not found");
            this.graph = new TikzGraph();
        }
    }

    public String getSource() throws IOException {
        return this.project.getDiagramSource(this.name);
    }

    public byte[] getDiff() throws IOException {
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
        if(undoRedoFlag){
            return;
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
        if(!diffString.trim().equals("")) {
            diffs.add(new Diff(new Date(), diffString));
        }
        this.writeDiffs(diffs);
        this.writeTikz(this.graph.toString());

        redoList = new ArrayList<>();

        RecentProjects.addProject(this.getProject());

    }

    /**
     * Getter for the tikz text contained in the save file of this project.
     *
     * @return The current saved tikz text
     * @throws IOException
     */
    public String getDiskTikz() throws IOException {
        return this.getSource();
    }


    public void rename(String newName) throws IOException {
        this.project.renameDiagram(this.name, newName);
        this.name = newName;
        RecentProjects.addProject(this.getProject());
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
        byte[] bytes = this.getDiff();
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
    private void writeDiffs(List<Diff> diffs) throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bs);
        os.writeObject(diffs);

        this.writeDiff(bs.toByteArray());

        os.close();
        bs.close();
    }

    private void writeDiff(byte[] bytes) throws IOException {
        this.project.writeDiff(this.name, bytes);
    }

    private void writeTikz(String tikz) throws IOException {
        this.project.writeSource(this.name, tikz);
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

    /**
     * function to undo an action in a project
     */
    public void undo() {
        List<Diff> diffs;
        try {
            diffs = this.getDiffs();
        } catch (IOException | ClassNotFoundException e) {
            logger.warning("Couldn't undo: " + e.toString());
            return;
        }
        if (diffs.isEmpty()) {return;}
        Diff last = diffs.remove(diffs.size() - 1);
        String original = this.graph.toString();
        apply_patch(last);
        final String tmp;
        try {
            tmp = DiffUtil.diff(original, this.graph.toString());
            redoList.add(new Diff(new Date(), tmp));
        } catch (UnsupportedEncodingException e) {
            logger.fine("Should not happen: " + e.toString());
        }
        write_applier(diffs);
    }

    /**
     * function to redo an action in a project
     */
    public void redo() {
        if (redoList.isEmpty()) {return; }
        Diff diff = redoList.remove(redoList.size()-1);
        String original = this.graph.toString();
        apply_patch(diff);
        String diffString = null;

        try {
            diffString = DiffUtil.diff(original, this.graph.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<Diff> diffs;
        try {
            diffs = this.getDiffs();
        } catch (IOException | ClassNotFoundException e) {
            diffs = new ArrayList<>();
        }

        diffs.add(new Diff(new Date(), diffString));
        write_applier(diffs);
    }

    /**
     * Apply a patch and replace graph with result
     * @param last a diff to change the tikz
     */
    private void apply_patch (Diff last) {
        undoRedoFlag = true;
        DiffMatchPatch undo = new DiffMatchPatch();
        final List<DiffMatchPatch.Patch> patches = undo.patchFromText(last.getPatch());
        String original = this.graph.toString();
        final Object[] modified = undo.patchApply((LinkedList<DiffMatchPatch.Patch>) patches, original);

        TikzGraph reversed = new TikzGraph();
        NodeParser.parseDocument(reversed).parse((String) modified[0]);


        this.graph.replace(reversed);
        undoRedoFlag = false;
    }

    /**
     * update list of diffs and save graph
     * @param diffs the list of diffs
     */
    private void write_applier (List<Diff> diffs){
        try {
            this.writeDiffs(diffs);
            this.writeTikz(this.graph.toString());
            RecentProjects.addProject(this.getProject());
        } catch (IOException e) {
            logger.warning("Couldn't save new diff history: " + e.toString());
        }
    }
}
