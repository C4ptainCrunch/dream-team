package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import models.project.Project;

/**
 * This class is a container for the recent Projects.
 *
 * A Project is considered as recent if the user made a specific operation with it.
 * The specific operations are: Open, Rename, Save and Create a project.
 */

public class RecentProjects {
    private final static Logger logger = Log.getLogger(RecentProjects.class);

    private static Path getFilePath() {
        return Dirs.getDataDir().resolve(Paths.get("recent.paths"));
    }

    /**
     * Retrieves the recent Projects by loading them from the correct directory.
     * @return A SortedSet of the Projects Loaded.
     */
    public static SortedSet<Project> getRecentProjects() {
        TreeSet<Project> projects = new TreeSet<>();

        Path recentProjectsPath = getFilePath();
        FileReader fileReader;
        try {
            fileReader = new FileReader(recentProjectsPath.toFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Project p = new Project(Paths.get(line));
                if(p.exists() && !p.isCloud()) {
                    try {
                        p.getName();
                        projects.add(p);
                    } catch (Exception e){
                        logger.fine("Invalid project found, skipping: " + p.getPath().toString());
                    }
                } else {
                    logger.fine("Project " + line + " does not exist anymore");
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            logger.info("Failed to read recent projects file");
            return projects;
        }

        return projects;
    }

    /**
     * Add a Project to the "Recent Projects". This method is called when
     * one of the four operations described above is performed.
     * @param project The Project to be added.
     * @throws IOException if an I/O error occurs (because this method call a private one that write the Project the a File).
     */
    public static void addProject(Project project) throws IOException {
        if(!project.isTemporary()) {
            SortedSet<Project> projects = getRecentProjects();

            // Add the project to the end (and don't have it twice in the list)
            projects.remove(project);
            projects.add(project);

            writeToDisk(projects);
        }
    }

    private static void writeToDisk(SortedSet<Project> projects) throws IOException {
        Files.createDirectories(getFilePath().getParent());
        FileWriter fw = new FileWriter(getFilePath().toFile());
        BufferedWriter bw = new BufferedWriter(fw);

        List<String> projectPaths = projects.stream().map(Project::getPath).map(Object::toString).collect(Collectors.toList());
        String txt = String.join(System.lineSeparator(), projectPaths);

        bw.write(txt);
        bw.close();
    }
}
