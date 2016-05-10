package models.project;

import com.sun.nio.zipfs.ZipFileSystem;
import jdk.nashorn.internal.ir.Node;
import utils.Log;
import utils.RecentProjects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class Project implements Comparable<Project>{
    private Path path;
    private boolean isTemporary = false;
    private final static Logger logger = Log.getLogger(Project.class);

    public Project(Path path) throws IOException {
        this.path = path;
    }

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

    public Path getDirectory() {
        return this.path.getParent();
    }

    synchronized public String getDiagramSource(String name) throws IOException {
        try (FileSystem fs = getFs()) {
            return new String(Files.readAllBytes(fs.getPath("/" + name + ".tikz")));
        }
    }

    synchronized public byte[] getDiagramDiff(String name) throws IOException {
        try (FileSystem fs = getFs()) {
            return Files.readAllBytes(fs.getPath("/" + name + ".diff"));
        }
    }

    public Diagram getDiagram(String name) {
        return new Diagram(name, this);
    }

    synchronized public void renameDiagram(String oldName, String newName) throws IOException {
        try (FileSystem fs = getFs()) {

            Path newSource = fs.getPath("/" + newName + ".tikz");
            Path oldSource = fs.getPath("/" + oldName + ".tikz");
            Files.move(oldSource, newSource);

            Path newDiff = fs.getPath("/" + newName + ".diff");
            Path oldDiff = fs.getPath("/" + oldName + ".diff");
            Files.move(oldDiff, newDiff);

            RecentProjects.addProject(this);
        }
    }

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

    public boolean isTemporary() {
        return isTemporary;
    }

    synchronized public void move(File newFile) throws IOException {
        this.isTemporary = false;
        Files.move(this.path, newFile.toPath());
        this.path = newFile.toPath();
        RecentProjects.addProject(this);
    }

    public Path getPath() {
        return path;
    }

    public String getName() {
        return this.path.getFileName().toString();
    }

    synchronized public Date getLastChange() throws IOException {
        return this.getDiagramNames().stream().map(name -> {
            try {
                return this.getDiagram(name).getLastChange();
            } catch (IOException | ClassNotFoundException e) {
                return null;
            }
        }).filter(recent -> recent != null).max(Comparator.comparing(e -> e)).get();
    }

    @Override
    public int compareTo(Project other) {
        return this.getPath().compareTo(other.getPath());
    }

    @Override
    public String toString() {
        return this.getPath().toString();
    }

    synchronized public void writeDiff(String name, byte[] bytes) throws IOException {
        try (FileSystem fs = getFs()) {
            Path diffPath = fs.getPath("/" + name + ".diff");
            Files.write(diffPath, bytes, TRUNCATE_EXISTING, CREATE);
            logger.fine("Diff written");
        }
    }

    synchronized public void writeSource(String name, String tikz) throws IOException {
        try (FileSystem fs = getFs()) {
            Path diffPath = fs.getPath("/" + name + ".tikz");
            Files.write(diffPath, tikz.getBytes(), TRUNCATE_EXISTING, CREATE);
            logger.fine("Source written");
        }
    }
}
