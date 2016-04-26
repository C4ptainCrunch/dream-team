package models.project;


import models.tikz.TikzGraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class TikzIO {

    protected TikzGraph graph;

    protected TikzIO(){
        this.graph = null;
    }

    protected TikzIO(TikzGraph g){
        this.graph = g;
    }

    protected static File createSaveFile(String filename, String directory) throws IOException {
        Path dir = Paths.get(directory);
        File tikz_file = dir.resolve(filename).toFile();
        if (!tikz_file.exists()) {
            new FileOutputStream(tikz_file).close();
        }
        return tikz_file;
    }

    /**
     * Writes the tikz source of the project to the save file
     *
     * @param tikz
     *            : the tikz source
     * @throws IOException
     *             when writing failed
     */

    protected void writeTikz(String tikz, File file) throws IOException {
        PrintWriter sourceWriter = new PrintWriter(file);
        sourceWriter.println(tikz);
        sourceWriter.close();
    }

    protected void writeTikz(String tikz, Path path) throws IOException {
        writeTikz(tikz, path.toFile());
    }

    /**
     * Getter for the tikz graph of this project
     *
     * @return the tikz graph
     */

    public TikzGraph getGraph() {
        return graph;
    }

    /**
     * Setter for the tikz graph linked to this project
     *
     * @param graph
     *            The tikz graph
     */
    public void setGraph(TikzGraph graph) {
        this.graph = graph;
    }
}
