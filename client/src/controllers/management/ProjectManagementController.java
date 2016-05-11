package controllers.management;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.util.logging.Logger;

import javax.swing.*;

import models.project.Diagram;
import models.project.Project;
import utils.Log;
import utils.RecentProjects;
import views.editor.EditorView;
import views.management.DiagramManagementView;
import views.management.FileChooseView;
import views.management.ProjectManagementView;
import constants.Errors;
import constants.GUI.ProjectManagement;

/**
 * Controller for the ProjectManagementView
 */

public class ProjectManagementController {
    private final static Logger logger = Log.getLogger(ProjectManagementController.class);
    private final ProjectManagementView view;

    /**
     * Creates a controller for the ProjectManagementView
     * @param view The corresponding view, created elsewhere
     */
    public ProjectManagementController(ProjectManagementView view) {
        this.view = view;
    }

    /**
     * Closes the current view and prompts the user to open a new diagram in the
     * current project.
     * @param project The current project
     * @throws IOException
     */
    public void editProject(Project project) throws IOException {
        RecentProjects.addProject(project);

        new DiagramManagementView(project);
        this.view. dispose();
    }

    /**
     * Launches edition of a given diagram
     * @param diagram The diagram to be edited
     * @throws IOException
     */
    public void editDiagram(Diagram diagram) throws IOException {
        RecentProjects.addProject(diagram.getProject());

        java.awt.EventQueue.invokeLater(() -> new EditorView(diagram));
        this.view.dispose(); // Exit previous windows
    }

    /**
     * Updates the project description with the selected project
     * Should be called when the dropdown is updated.
     * @param selectedProject
     */
    public void dropdownSelected(Project selectedProject) {
        if(selectedProject == null){
            return;
        }

        String text = null;
        try {
            text = String.format(ProjectManagement.BLANK_INFO_PANEL, selectedProject.getName(), "Local",
                        selectedProject.getLastChange().toString());
        } catch (FileSystemNotFoundException e) {
            logger.fine("Get last change from project failed");
        } catch (IOException e) {
            logger.fine("Get last change from project failed");
        }
        this.view.setInfoText(text);

    }

    /**
     * Creates a new project and opens it in the editor.
     */
    public void createProject() {
        try {
            editDiagram(new Project().getDiagram("unsaved"));
        } catch (IOException e) {}
    }


    /**
     * Opens for edition a project that was selected in the view.
     */
    public void openRecentProject() {
        Project project = view.getSelectedProject();
        if (project != null) {
            try {
                editProject(project);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, Errors.OPEN_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
                logger.severe("Failed to open the diagram: " + e.getMessage());
            }
        }
    }

    /**
     * Opens a FileChooser to select an existing project in the file system
     */
    public void openProject(){

        FileChooseView choose = new FileChooseView("Select project", JFileChooser.FILES_ONLY);
        choose.setFileRestriction("CreaTikz files","crea");
        File projectFile = choose.ask();
        if(projectFile != null) {
            try {
                Project currentProject = new Project(projectFile.toPath());
                new DiagramManagementView(currentProject);
                this.view.dispose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Renames and/or moves a project in the file system
     */
    public void moveProject() {
        Project project = view.getSelectedProject();
        if (project == null) {
            return;
        }

        FileChooseView choose = new FileChooseView("Move project", JFileChooser.DIRECTORIES_ONLY);
        File path = choose.ask();
        if (path != null) {
            try {
                project.move(path);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, Errors.RENAME_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
                logger.severe("Failed to rename the diagram: " + e.getMessage());
            }
        }
    }

    public void uploadProject() {
        Project project = view.getSelectedProject();
        if (project == null) {
            return;
        }

        // TODO : upload project to server
    }
}
