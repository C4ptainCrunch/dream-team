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
import models.tikz.RecentProjects;

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
//
//    public String choosePath(){
//        java.io.File f = createPanel(ProjectManagement.CREATE_PANEL, JFileChooser.DIRECTORIES_ONLY);
//        if (f != null) {
//
//        String path = f.getAbsolutePath();
//
//            if (f.exists()) {
//                JOptionPane.showMessageDialog(this.view, Warnings.FILE_WARNING,
//                        String.format(Warnings.WARNING_TYPE, "File"), JOptionPane.WARNING_MESSAGE);
//            } else {
//                try {
//                    f.mkdir();
//                    addProjectPathToSavedPathFile(filePath);
//                    java.awt.EventQueue.invokeLater(() -> new EditorView(filePath, false));
//                    view.dispose(); // Exit previous windows
//                } catch (SecurityException e) {
//                    JOptionPane.showMessageDialog(this.view, Warnings.PERMISSION_WARNING,
//                            String.format(Warnings.WARNING_TYPE, "Security"), JOptionPane.WARNING_MESSAGE);
//                }
//            }
//        }
//    }
//
//    public void importProject() {
//            java.io.File f = createPanel(ProjectManagement.IMPORT_PANEL, JFileChooser.FILES_ONLY);
//            if (f != null) {
//                String filePath = f.getAbsolutePath();
//                int endIndex = filePath.lastIndexOf("/");
//                addProjectPathToSavedPathFile(filePath.substring(0, endIndex));
//                java.awt.EventQueue.invokeLater(() -> new EditorView(filePath, true));
//                view.dispose(); // Exit previous windows
//            }
//    }
//
//


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
}
