package gui.projectManagement.controllers;


import gui.editor.views.EditorView;
import gui.projectManagement.views.ProjectManagementView;
import constants.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class ProjectManagementController {
    private ProjectManagementView view;

    public ProjectManagementController(ProjectManagementView view){
        this.view = view;
    }

    private java.io.File getSavedPathsFileFromPath(String path){
        String homeDir = System.getProperty("user.home");
        path = homeDir + path;
        String fileSeparator = System.getProperty("file.separator");

        java.io.File savedDir = new java.io.File(path);
        // Default for now, will receive current username when accounts exist.
        java.io.File savedPathsFile = new java.io.File(path+fileSeparator+"default.paths");

        System.out.println(path);
        System.out.println(path+fileSeparator+"default.paths");

        if(! savedDir.exists()){
            savedDir.mkdirs();
            try {
                savedPathsFile.createNewFile();
            } catch (IOException e) {  // Not logical but necessary
                System.err.println("Exists: " + e.getMessage());
            }
        }

        return savedPathsFile;
    }

    private java.io.File getSavedPathsFile(){
        java.io.File savedPathsFile;

        switch (System.getProperty("os.name")){
            case "Linux":
                savedPathsFile = getSavedPathsFileFromPath(Utils.LINUX_PATH);
                break;
            case "Mac OS X":
                savedPathsFile = getSavedPathsFileFromPath(Utils.MAC_PATH);
                break;
            default:  // Unfortunately, default is Windows OS...
                String windowsPath = Utils.WINDOWS_PATH_ONE + System.getProperty("user.name") + Utils.WINDOWS_PATH_TWO;
                savedPathsFile = getSavedPathsFileFromPath(windowsPath);
                break;
        }

        return savedPathsFile;
    }

    public void createProject() {

        java.io.File f = createPanel("Choose location to create your project", JFileChooser.DIRECTORIES_ONLY);
        if (f != null){
            String filePath = f.getAbsolutePath();

            if(!f.exists()){
                try{
                    f.mkdir();
                    java.io.File savedPathsFile = getSavedPathsFile();

                    try(FileWriter fw = new FileWriter(savedPathsFile, true);
                        BufferedWriter bw = new BufferedWriter(fw)
                        )
                    {
                        bw.append(filePath+"\r\n");
                    }
                    catch (IOException e) {

                    }

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
