package controllers.management;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.swing.*;

import models.project.Project;
import models.project.RecentProjects;
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

    public void dropdownSelected(JComboBox comboBox) {
        Project selectedProject = (Project) comboBox.getSelectedItem();
        logger.info(selectedProject.toString());

        try {
            String text = String.format(ProjectManagement.BLANK_INFO_PANEL, selectedProject.getName(), "Local",
                    selectedProject.getLastChange().toString());
            this.view.setInfoText(text);
        } catch (IOException | ClassNotFoundException e) {
            logger.severe("Error while reading the diff file: " + e.toString());
        }
    }

    public void createProject() {
        FileChooseView choose = new FileChooseView("Create project", JFileChooser.DIRECTORIES_ONLY);
        Path path = choose.ask().toPath();
        if (path != null) {
            try {
                Project p = new Project(path);
                editProject(p);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, Errors.CREATE_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
                logger.severe("Failed to create new project: " + e.getMessage());
            }
        }
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
