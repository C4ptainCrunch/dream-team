package gui.projectManagement.controllers;

import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import constants.GUI.ProjectManagement;
import constants.Warnings;
import gui.editor.views.EditorView;
import gui.projectManagement.views.ProjectManagementView;
import models.Project;

public class ProjectManagementController {
    private final ProjectManagementView view;

    public ProjectManagementController(ProjectManagementView view) {
        this.view = view;
    }

    private void updateSavedProjectsFile(String oldName, String newName) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(getProjectsListFilePath()));
        String line;
        String input = "";

        while ((line = file.readLine()) != null) {
            input += line + System.lineSeparator();
        }

        input = input.replace(oldName, newName);

        FileOutputStream os = new FileOutputStream(getProjectsListFilePath());
        os.write(input.getBytes());

        file.close();
        os.close();
    }

    private void addProjectPathToSavedPathFile(String projectPath) {
        List<String> paths = getSavedPaths();
        if (!paths.contains(projectPath)) {
            String path = getProjectsListFilePath();
            java.io.File savedPathsFile = getSavedPathsFileFromPath(path);

            try (FileWriter fw = new FileWriter(savedPathsFile, true); BufferedWriter bw = new BufferedWriter(fw)) {
                bw.append(projectPath + System.lineSeparator());
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

    public void openProject(String path) {
        Project project = Project.fromPath(path);
        java.awt.EventQueue.invokeLater(() -> new EditorView(project));
    }

    public String choosePath(){
        java.io.File f = createPanel(ProjectManagement.CREATE_PANEL, JFileChooser.DIRECTORIES_ONLY);
        if (f != null) {

        String path = f.getAbsolutePath();

            if (f.exists()) {
                JOptionPane.showMessageDialog(this.view, Warnings.FILE_WARNING,
                        String.format(Warnings.WARNING_TYPE, "File"), JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    f.mkdir();
                    addProjectPathToSavedPathFile(filePath);
                    java.awt.EventQueue.invokeLater(() -> new EditorView(filePath, false));
                    view.dispose(); // Exit previous windows
                } catch (SecurityException e) {
                    JOptionPane.showMessageDialog(this.view, Warnings.PERMISSION_WARNING,
                            String.format(Warnings.WARNING_TYPE, "Security"), JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    public void importProject() {
            java.io.File f = createPanel(ProjectManagement.IMPORT_PANEL, JFileChooser.FILES_ONLY);
            if (f != null) {
                String filePath = f.getAbsolutePath();
                int endIndex = filePath.lastIndexOf("/");
                addProjectPathToSavedPathFile(filePath.substring(0, endIndex));
                java.awt.EventQueue.invokeLater(() -> new EditorView(filePath, true));
                view.dispose(); // Exit previous windows
            }
    }

    public void openProject() {
            String filePath = this.view.getSelectedPath() + System.getProperty("file.separator") + "tikz.save";
            java.awt.EventQueue.invokeLater(() -> new EditorView(filePath, true));
            view.dispose(); // Exit previous windows
    }

    private java.io.File createPanel(String title, int selectionMode) {
        System.out.println("create panel");
        JPanel panel = new JPanel();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setDialogTitle(title);
        fileChooser.setFileSelectionMode(selectionMode);

        if (fileChooser.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): " + fileChooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : " + fileChooser.getSelectedFile());
            return fileChooser.getSelectedFile();
        } else {
            System.out.println("No Selection ");
            return null;
        }
    }

    public void renameProject() {
        String selectedPath = this.view.getSelectedPath();
        int endIndex = selectedPath.lastIndexOf("/");
        java.io.File dir = new java.io.File(selectedPath);
        String newProjectName = JOptionPane.showInputDialog("New project name");
        String newName = selectedPath.substring(0, endIndex) + System.getProperty("file.separator")
                + newProjectName;
        java.io.File newDir = new java.io.File(newName);
        if (dir.isDirectory() && newProjectName != null) {
            dir.renameTo(newDir);
            try {
                this.updateSavedProjectsFile(selectedPath, newName);
            } catch (IOException e) {
                e.getStackTrace();
            }
            this.view.updateComboBox(newName);
        }

    }

    // TODO : move this to a model
    public String getLastRevision() {
        String ch = "";

        try {
            String pathToRevisionFile = this.view.getSelectedPath();
            ArrayList tmp = new ArrayList<String>();
            FileReader fr = new FileReader(pathToRevisionFile + System.getProperty("file.separator") + "diffs");
            BufferedReader br = new BufferedReader(fr);

            do {
                try {
                    ch = br.readLine();
                } catch (IOException e) {
                    e.getStackTrace();
                }
                tmp.add(ch);
            } while (!ch.startsWith("2016"));
        } catch (FileNotFoundException e) {
            e.getStackTrace();
        }
        return ch;
    }

    public void dropdownSelected(ActionEvent event) {
        JComboBox comboBox = (JComboBox) event.getSource();
        String selectedProject = comboBox.getSelectedItem().toString();

        int endIndex = selectedProject.lastIndexOf("/");
        String projectName = selectedProject.substring(endIndex + 1, selectedProject.length());
        String lastRevision = this.getLastRevision();

        String text = String.format(ProjectManagement.BLANK_INFO_PANEL, projectName, "Local", lastRevision);
        this.view.setInfoText(text);
    }

    public void createProject() {

    }
}
