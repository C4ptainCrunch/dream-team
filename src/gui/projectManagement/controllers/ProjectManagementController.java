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

            if(!f.exists()){
                try{
                    f.mkdir();
                    java.awt.EventQueue.invokeLater(()->new EditorView(filePath, false));
                    view.dispose(); //Exit previous windows
                }catch(SecurityException e){
                    e.getStackTrace();
                    //TODO: Alerte erreur
                }
            }
            else{
                //TODO:Alerte file already exists
            }
        }
    }

    public void importProject() {
        java.io.File f = createPanel("Choose location to import your project", JFileChooser.FILES_ONLY);
        if (f != null){
            String filePath = f.getAbsolutePath();
            java.awt.EventQueue.invokeLater(()->new EditorView(filePath, true));
            view.dispose(); //Exit previous windows
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
