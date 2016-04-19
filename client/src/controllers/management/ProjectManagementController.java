package controllers.management;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.*;

import constants.Errors;
import constants.GUI.ProjectManagement;
import views.editor.EditorView;
import views.management.FileChooseView;
import views.management.ProjectManagementView;
import models.project.Project;
import models.project.RecentProjects;
import utils.Log;

public class ProjectManagementController {
    private final ProjectManagementView view;
    private final static Logger logger = Log.getLogger(ProjectManagementController.class);

    public ProjectManagementController(ProjectManagementView view) {
        this.view = view;
    }

    public void editProject(String path) throws IOException {
        Project project = Project.fromPath(path);
        RecentProjects.addProject(project);

        java.awt.EventQueue.invokeLater(() -> new EditorView(project));
        view.dispose(); // Exit previous windows
    }

    public void dropdownSelected(ActionEvent event) {
        JComboBox comboBox = (JComboBox) event.getSource();
        Project selectedProject = (Project) comboBox.getSelectedItem();

        try {
            String text = String.format(
                    ProjectManagement.BLANK_INFO_PANEL,
                    selectedProject.getName(),
                    "Local",
                    selectedProject.getLastChange().toString()
            );
            this.view.setInfoText(text);
        } catch (IOException | ClassNotFoundException e) {
            logger.severe("Error while reading the diff file: " + e.toString());
        }
    }

    public void createProject() {
        FileChooseView choose = new FileChooseView("Create project", JFileChooser.DIRECTORIES_ONLY);
        File path = choose.ask();
        if(path != null) {
            try {
                Project.initialize(path);
                editProject(path.getPath().toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, Errors.CREATE_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
                logger.severe("Failed to create new project: " + e.getMessage());
            }
        }
    }

    public void openProject() {
        Project project = view.getSelectedProject();
        if(project != null) {
            try {
                editProject(project.getPath().toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, Errors.OPEN_ERROR , Errors.ERROR, JOptionPane.ERROR_MESSAGE);
                logger.severe("Failed to open the project: " + e.getMessage());
            }
        }
    }

    public void renameProject() {
        Project project = view.getSelectedProject();
        if(project == null) {
            return ;
        }

        FileChooseView choose = new FileChooseView("Rename project", JFileChooser.DIRECTORIES_ONLY);
        File path = choose.ask();
        if(path != null) {
            try {
                project.rename(path);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, Errors.RENAME_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
                logger.severe("Failed to rename the project: " + e.getMessage());
            }
        }
    }
}
