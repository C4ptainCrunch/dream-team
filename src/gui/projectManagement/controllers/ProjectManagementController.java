package gui.projectManagement.controllers;


import gui.editor.views.EditorView;
import gui.projectManagement.views.ProjectManagementView;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class ProjectManagementController {
    private ProjectManagementView view;

    public ProjectManagementController(ProjectManagementView view){
        this.view = view;
    }

    public void createProject() {
        java.io.File f = createPanel("Choose location to create your project", JFileChooser.DIRECTORIES_ONLY);
        if (f != null){
            String filePath = f.getAbsolutePath();
            java.awt.EventQueue.invokeLater(()->new EditorView(filePath));
            view.dispose(); 
        }
    }

    public void importProject() {
        java.io.File f = createPanel("Choose location to import your project", JFileChooser.FILES_ONLY);
        if (f != null){
            String filePath = f.getAbsolutePath();
        }
    }

    private java.io.File createPanel(String title, int selectionMode) {
        System.out.println("create panel");
        JPanel panel = new JPanel();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setDialogTitle(title);
        fileChooser.setFileSelectionMode(selectionMode);

        if (fileChooser.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): " +  fileChooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : " +  fileChooser.getSelectedFile());
            return fileChooser.getSelectedFile();
        }
        else {
            System.out.println("No Selection ");
            return null;
        }
    }


}
