package models.project;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.zip.ZipOutputStream;

import utils.Log;
import utils.RecentProjects;

/**
 * A project represents a .crea file in a zip format.
 * It contains multiple diagrams stored on the disk as
 * files in the zip (diagram_name.tikz and diagram_name.diff).
 * All read and writes to the zip are synchronised to avoid race-conditions
 */
public class Project extends Observable implements Comparable<Project>{
    private Path path;
    private boolean isTemporary = false;
    private final static Logger logger = Log.getLogger(Project.class);

    /**
     * Create a Project from a .crea file on the disk
     * @param path path to the .crea file
     * @throws IOException
     */
    public Project(Path path) throws IOException {
        this.path = path;
    }

    /**
     * Create a Project in a temporary (os-dependant) location
     * @throws IOException
     */
    public Project() throws IOException {
        this(createTempZip());
        this.isTemporary = true;
    }

    private static Path createTempZip() throws IOException {
        Path p = File.createTempFile("creatikz-project", ".zip").toPath();
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(p.toString()));
        zip.close();
        return p;
    }

    private FileSystem getFs() throws IOException {
        return FileSystems.newFileSystem(path, null);
    }

    /**
     * Get the directory where the .crea is located
     * @return the directory path
     */
    public Path getDirectory() {
        return this.path.getParent();
    }

    /**
     * Get the tikz source of the diagram
     * @param name the diagram name
     * @return the source
     * @throws IOException
     */
    synchronized public String getDiagramSource(String name) throws IOException {
        try (FileSystem fs = getFs()) {
            return new String(Files.readAllBytes(fs.getPath("/" + name + ".tikz")));
        }
    }

    /**
     * Get the tikz binary diff of the diagram
     * @param name the diagram name
     * @return the binary diff
     * @throws IOException
     */
    synchronized public byte[] getDiagramDiff(String name) throws IOException {
        try (FileSystem fs = getFs()) {
            return Files.readAllBytes(fs.getPath("/" + name + ".diff"));
        }
    }

    /**
     * Instantiates a diagram from files contained in the .crea
     * @param name the name of the diagram
     * @return
     */
    public Diagram getDiagram(String name) {
        return new Diagram(name, this);
    }

    /**
     * Renames a diagram in the .crea (moves the files)
     * @param oldName
     * @param newName
     * @throws IOException
     */
    synchronized public void renameDiagram(String oldName, String newName) throws IOException {
        try (FileSystem fs = getFs()) {

            Path newSource = fs.getPath("/" + newName + ".tikz");
            Path oldSource = fs.getPath("/" + oldName + ".tikz");
            Files.move(oldSource, newSource);

            Path newDiff = fs.getPath("/" + newName + ".diff");
            Path oldDiff = fs.getPath("/" + oldName + ".diff");
            Files.move(oldDiff, newDiff);

            RecentProjects.addProject(this);
            this.setChanged();
            this.notifyObservers();
        }
    }

    /**
     * Get a set of all diagram names contained in the project
     * @return the set of names
     * @throws IOException
     */
    synchronized public Set<String> getDiagramNames() throws IOException {
        Set<String> names = new HashSet<>();
        try (FileSystem fs = getFs()) {

            Iterable<Path> dirs = fs.getRootDirectories();

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirs.iterator().next())) {
                for (Path file : stream) {
                    String name = file.getFileName().toString();
                    if (name.indexOf(".") > 0) {
                        name = name.substring(0, name.lastIndexOf("."));
                    }
                    names.add(name);
                }
            } catch (IOException | DirectoryIteratorException e) {
                e.printStackTrace();
            }
            return names;
        }
    }

    /**
     * @return True if the .crea is located in a (os-dependant) location
     */
    public boolean isTemporary() {
        return isTemporary;
    }

    synchronized public void move(File newFile) throws IOException {
        this.isTemporary = false;
        Files.move(this.path, newFile.toPath());
        this.path = newFile.toPath();
        RecentProjects.addProject(this);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * @return the path to the .crea file
     */
    public Path getPath() {
        return path;
    }

    /**
     * @return the name of .crea file (with the extension)
     */
    public String getName() {
        return this.path.getFileName().toString();
    }

    /**
     * @return the date of the newest diagram change, Date(0) if none
     * @throws IOException
     */
    synchronized public Date getLastChange() throws IOException {
        try {
            return this.getDiagramNames().stream().map(name -> {
                try {
                    return this.getDiagram(name).getLastChange();
                } catch (IOException | ClassNotFoundException e) {
                    return null;
                }
            }).filter(recent -> recent != null).max(Comparator.comparing(e -> e)).get();
        } catch (NoSuchElementException e){
            return new Date(0);
        }
    }

    /**
     * Compare the project to another with their paths
     * @param other
     * @return standard Java comparison
     */
    @Override
    public int compareTo(Project other) {
        return this.getPath().compareTo(other.getPath());
    }

    /**
     * @return a string that is the filename of the project
     */
    @Override
    public String toString() {
        return this.getPath().toString();
    }

    /**
     * Write the given bytes to the given project diff file
     * @param name the project name
     * @param bytes the given bytes
     * @throws IOException
     */
    synchronized public void writeDiff(String name, byte[] bytes) throws IOException {
        try (FileSystem fs = getFs()) {
            Path diffPath = fs.getPath("/" + name + ".diff");
            Files.write(diffPath, bytes, TRUNCATE_EXISTING, CREATE);
            logger.fine("Diff written");
        }
    }

    /**
     * Write the given string to the given project tikz file
     * @param name the diagram name
     * @param tikz the tikz to write
     * @throws IOException
     */
    synchronized public void writeSource(String name, String tikz) throws IOException {
        try (FileSystem fs = getFs()) {
            Path sourcePath = fs.getPath("/" + name + ".tikz");
            Files.write(sourcePath, tikz.getBytes(), TRUNCATE_EXISTING, CREATE);
            logger.fine("Source written");
        }
    }

    /**
     * @return true if the project exists on the disk
     */
    public boolean exists() {
        return this.path.toFile().exists();
    }
}
