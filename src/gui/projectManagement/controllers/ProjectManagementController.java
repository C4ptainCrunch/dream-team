package gui.projectManagement.controllers;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import constants.GUI.ProjectManagement;
import gui.editor.views.EditorView;
import gui.projectManagement.views.FileChooseView;
import gui.projectManagement.views.ProjectManagementView;
import models.Project;
import models.RecentProjects;

public class ProjectManagementController {
    private final ProjectManagementView view;

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

        String text = String.format(
                ProjectManagement.BLANK_INFO_PANEL,
                selectedProject.getName(),
                "Local",
                selectedProject.getLastRevision()
        );
        this.view.setInfoText(text);
    }

    public void createProject() {
        FileChooseView choose = new FileChooseView("Create project", JFileChooser.DIRECTORIES_ONLY);
        File path = choose.ask();
        if(path != null) {
            try {
                Project.initialize(path);
                editProject(path.getPath().toString());
            } catch (IOException e) {
                e.printStackTrace();
                // TODO : warn user
            }
        }
    }

    public void openProject() {
        Project project = view.getSelectedProject();
        if(project != null) {
            try {
                editProject(project.getPath().toString());
            } catch (IOException e) {
                e.printStackTrace();
                // TODO : warn user
            }
        }
    }

    public void renameProject() {
        Project project = view.getSelectedProject();
        if(project == null) {
            // TODO : log this
            return ;
        }

        FileChooseView choose = new FileChooseView("Rename project", JFileChooser.DIRECTORIES_ONLY);
        File path = choose.ask();
        if(path != null) {
            try {
                project.rename(path);
            } catch (IOException e) {
                e.printStackTrace();
                // TODO : warn user
            }
        }
    }
}
