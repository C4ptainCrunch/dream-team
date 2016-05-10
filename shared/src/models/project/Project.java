package models.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipOutputStream;

public class Project {
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
    }

    public Set<String> getDiagramNames() {
        Set<String> names = new HashSet<>();

        for(Path p :fs.getRootDirectories()) {
            String name = p.getFileName().toString();
            if (name.indexOf(".") > 0){
                name = name.substring(0, name.lastIndexOf("."));
            }
            names.add(name);
        }

        return names;
    }

    public boolean isTemporary() {
        return isTemporary;
    }

    public void move(File newFile) {
        // TODO nikita : move zip
    }
}
