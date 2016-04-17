package models;


import utils.Dirs;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import java.util.stream.Collectors;

// TODO : this class is not really a model but i don't want to place it in Utils... Where should it go ?
public class RecentProjects {
    private static Path getFilePath() {
        return Dirs.getDataDir().resolve(Paths.get("recent.paths"));
    }
    public static SortedSet<Project> getRecentProjects() throws IOException {
        TreeSet<Project> projects = new TreeSet<>();

        Path rectentProjectsPath = getFilePath();
        FileReader fileReader;
        try {
            fileReader = new FileReader(rectentProjectsPath.toFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    projects.add(Project.fromPath(line));
                } catch (IOException e) {
                    // If there is an error while loading the project
                    // we can safely ignore it
                    // TODO : log the error (logger.debug)
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            // TODO : log the fact that the file does not exist
            e.printStackTrace();
            return projects;
        }

        return projects;
    }

    public static void addProject(Project project) throws IOException {
        SortedSet<Project> projects = getRecentProjects();

        // Add the project to the end (and don't have it twice in the list)
        projects.remove(project);
        projects.add(project);

        writeToDisk(projects);
    }

    private static void writeToDisk(SortedSet<Project> projects) throws IOException {
        FileWriter fw = new FileWriter(getFilePath().toFile());
        BufferedWriter bw = new BufferedWriter(fw);

        List<String> projectPaths = projects.stream().map(Project::getPath).collect(Collectors.toList());
        String txt = String.join(System.lineSeparator(), projectPaths);

        bw.write(txt);
        bw.close();
    }
}
