package controllers.management;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.*;

import models.project.Project;
import utils.RecentProjects;
import utils.Log;
import views.editor.EditorView;
import views.management.FileChooseView;
import views.management.ProjectManagementView;
import constants.Errors;
import constants.GUI.ProjectManagement;

public class ProjectManagementController {
    private final static Logger logger = Log.getLogger(ProjectManagementController.class);
    private final ProjectManagementView view;

    public ProjectManagementController(ProjectManagementView view) {
        this.view = view;
    }

    public void editProject(Project project) throws IOException {
        RecentProjects.addProject(project);

        java.awt.EventQueue.invokeLater(() -> new EditorView(project));
        view.dispose(); // Exit previous windows
    }

    /**
     * Updates the project description with the selected project
     * Should be called when the dropdown is updated.
     * @param comboBox
     */
    public void dropdownSelected(JComboBox comboBox) {
        Project selectedProject = (Project) comboBox.getSelectedItem();
        if(selectedProject == null){
            return;
        }
        logger.info(selectedProject.toString());

        try {
            String text = String.format(ProjectManagement.BLANK_INFO_PANEL, selectedProject.getName(), "Local",
                    selectedProject.getLastChange().toString());
            this.view.setInfoText(text);
        } catch (IOException | ClassNotFoundException e) {
            logger.severe("Error while reading the diff file: " + e.toString());
        }
    }

    /**
     * Creates a new project and opens it in the editor.
     */
    public void createProject() {
        try {
            editProject(new Project());
        } catch (IOException e) {}
    }


    public void openProject() {
        Project project = view.getSelectedProject();
        if (project != null) {
            try {
                editProject(project);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, Errors.OPEN_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
                logger.severe("Failed to open the project: " + e.getMessage());
            }
        }
    }

    public void renameProject() {
        Project project = view.getSelectedProject();
        if (project == null) {
            return;
        }

        FileChooseView choose = new FileChooseView("Rename project", JFileChooser.DIRECTORIES_ONLY);
        File path = choose.ask();
        if (path != null) {
            try {
                project.rename(path);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, Errors.RENAME_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
                logger.severe("Failed to rename the project: " + e.getMessage());
            }
        }
    }
}
