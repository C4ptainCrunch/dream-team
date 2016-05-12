package controllers.management;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.util.logging.Logger;

import javax.swing.*;

import misc.logic.CloudLogic;
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
                RecentProjects.addProject(project);
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

        try {
            CloudLogic.cloudifyProject(project);
            this.view.getParentView().refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
