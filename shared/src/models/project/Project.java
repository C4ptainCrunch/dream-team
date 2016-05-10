package models.project;

import com.sun.nio.zipfs.ZipFileSystem;
import jdk.nashorn.internal.ir.Node;
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
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

public class Project implements Comparable<Project>{
    private Path path;
    private FileSystem fs;
    private boolean isTemporary = false;

    public Project(Path path) throws IOException {
        this.path = path;
        this.fs = FileSystems.newFileSystem(path, null);
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

    public Path getDirectory() {
        return this.path.getParent();
    }

    public Path getDiagramSource(String name) {
        return this.fs.getPath("/" + name + ".tikz");
    }

    public Path getDiagramDiff(String name) {
        return this.fs.getPath("/" + name + ".diff");
    }

    public Diagram getDiagram(String name) {
        return new Diagram(name, this);
    }

    public void renameDiagram(String oldName, String newName) throws IOException {
        Path newSource = this.fs.getPath("/" + newName + ".tikz");
        Files.move(this.getDiagramSource(oldName), newSource);

        Path newDiff = this.fs.getPath("/" + newName + ".diff");
        Files.move(this.getDiagramDiff(oldName), newDiff);

        RecentProjects.addProject(this);
        this.sync();
    }

    public Set<String> getDiagramNames() {
        Set<String> names = new HashSet<>();

        Iterable<Path> dirs = fs.getRootDirectories();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirs.iterator().next())) {
            for (Path file: stream) {
                String name = file.getFileName().toString();
                if (name.indexOf(".") > 0){
                    name = name.substring(0, name.lastIndexOf("."));
                }
                names.add(name);
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }

        return names;
    }

    public boolean isTemporary() {
        return isTemporary;
    }

    public void move(File newFile) throws IOException {
        this.isTemporary = false;
        this.fs.close();
        Files.move(this.path, newFile.toPath());
        this.path = newFile.toPath();
        this.fs = FileSystems.newFileSystem(this.path, null);
        RecentProjects.addProject(this);
    }

    public void sync() {
        // http://mail.openjdk.java.net/pipermail/nio-dev/2012-July/001764.html
        try {
            Method m = ZipFileSystem.class.getDeclaredMethod("sync");
            m.setAccessible(true);//Abracadabra
            m.invoke(this.fs);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Path getPath() {
        return path;
    }

    public String getName() {
        return this.path.getFileName().toString();
    }

    public Date getLastChange() {
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
}
