package models.project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import utils.Dirs;
import utils.Log;

// TODO : this class is not really a model but i don't want to place it in Utils... Where should it go ?
public class RecentProjects {
    private final static Logger logger = Log.getLogger(RecentProjects.class);

    private static Path getFilePath() {
        return Dirs.getDataDir().resolve(Paths.get("recent.paths"));
    }

    public static SortedSet<Diagram> getRecentProjects() {
        TreeSet<Diagram> diagrams = new TreeSet<>();

        Path recentProjectsPath = getFilePath();
        FileReader fileReader;
        try {
            fileReader = new FileReader(recentProjectsPath.toFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Diagram p = new Diagram(Paths.get(line));
                if(p.exists()){
                    diagrams.add(p);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            logger.info("Failed to read recent diagrams file");
            return diagrams;
        }

        return diagrams;
    }

    public static void addProject(Diagram diagram) throws IOException {
        if(!diagram.isTemporary()) {
            SortedSet<Diagram> diagrams = getRecentProjects();

            // Add the diagram to the end (and don't have it twice in the list)
            diagrams.remove(diagram);
            diagrams.add(diagram);

            writeToDisk(diagrams);
        }
    }

    private static void writeToDisk(SortedSet<Diagram> diagrams) throws IOException {
        Files.createDirectories(getFilePath().getParent());
        FileWriter fw = new FileWriter(getFilePath().toFile());
        BufferedWriter bw = new BufferedWriter(fw);

        List<String> projectPaths = diagrams.stream().map(Diagram::getPath).map(Object::toString).collect(Collectors.toList());
        String txt = String.join(System.lineSeparator(), projectPaths);

        bw.write(txt);
        bw.close();
    }
}
