package gui.projectManagement.controllers;

import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import constants.GUI.ProjectManagementText;
import constants.Utils;
import constants.Warnings;
import gui.editor.views.EditorView;
import gui.projectManagement.views.ProjectManagementView;

public class ProjectManagementController {
    private final ProjectManagementView view;

    public ProjectManagementController(ProjectManagementView view) {
        this.view = view;
    }

    private List<String> getArrayListSavedPaths() {
        String path = getSavedPath();
        List<String> lines = new ArrayList<>();

        lines.add(ProjectManagementText.DROPDOWN_HEADER);

        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return lines;
    }

    public String[] getStringListSavedPaths() {
        List<String> lines = getArrayListSavedPaths();
        return lines.toArray(new String[lines.size()]);
    }

    private String getSavedPath() {
        String homeDir = System.getProperty("user.home");
        String fileSeparator = System.getProperty("file.separator");
        // Default for now, will receive current username when accounts exist.
        String filename = fileSeparator + "default.paths";
        switch (System.getProperty("os.name")) {
        case "Linux":
            return homeDir + Utils.LINUX_PATH + filename;
        case "Mac OS X":
            return homeDir + Utils.MAC_PATH + filename;
        default: // Unfortunately, default is Windows OS...
            String windowsPath = Utils.WINDOWS_PATH_ONE + System.getProperty("user.name") + Utils.WINDOWS_PATH_TWO;
            return homeDir + windowsPath + filename;
        }
    }

    private java.io.File getSavedPathsFileFromPath(String path) {
        java.io.File savedPathsFile = new java.io.File(path);

        if (!savedPathsFile.exists()) {
            try {
                savedPathsFile.getParentFile().mkdirs();
                savedPathsFile.createNewFile();
            } catch (IOException e) { // Not logical but necessary
                e.getStackTrace();
            }
        }

        return savedPathsFile;
    }

    private void updateSavedProjectsFile(String oldName, String newName) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(getSavedPath()));
        String line;
        String input = "";

        while ((line = file.readLine()) != null) {
            input += line + System.lineSeparator();
        }

        input = input.replace(oldName, newName);

        FileOutputStream os = new FileOutputStream(getSavedPath());
        os.write(input.getBytes());

        file.close();
        os.close();
    }

    private void addProjectPathToSavedPathFile(String projectPath) {
        List<String> paths = getArrayListSavedPaths();
        if (!paths.contains(projectPath)) {
            String path = getSavedPath();
            java.io.File savedPathsFile = getSavedPathsFileFromPath(path);

            try (FileWriter fw = new FileWriter(savedPathsFile, true); BufferedWriter bw = new BufferedWriter(fw)) {
                bw.append(projectPath + System.lineSeparator());
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

    public void createProject() {

        java.io.File f = createPanel(ProjectManagementText.CREATE_PANEL, JFileChooser.DIRECTORIES_ONLY);
        if (f != null) {
            String filePath = f.getAbsolutePath();

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
        if (this.view.getSelectedPathIndex() == 0) {
            java.io.File f = createPanel(ProjectManagementText.IMPORT_PANEL, JFileChooser.FILES_ONLY);
            if (f != null) {
                String filePath = f.getAbsolutePath();
                int endIndex = filePath.lastIndexOf("/");
                addProjectPathToSavedPathFile(filePath.substring(0, endIndex));
                java.awt.EventQueue.invokeLater(() -> new EditorView(filePath, true));
                view.dispose(); // Exit previous windows
            }
        } else {
            String filePath = this.view.getSelectedPath() + System.getProperty("file.separator") + "tikz.save";
            java.awt.EventQueue.invokeLater(() -> new EditorView(filePath, true));
            view.dispose(); // Exit previous windows
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
            System.out.println("getCurrentDirectory(): " + fileChooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : " + fileChooser.getSelectedFile());
            return fileChooser.getSelectedFile();
        } else {
            System.out.println("No Selection ");
            return null;
        }
    }

    public void renameProject() {
        int selectedPathIndex = this.view.getSelectedPathIndex();
        // 0 is the "Choose existing project..." in JComboBox
        if (selectedPathIndex == 0) {
            JOptionPane.showMessageDialog(this.view, Warnings.RENAME_WARNING,
                    String.format(Warnings.WARNING_TYPE, "Rename"), JOptionPane.WARNING_MESSAGE);
        } else {
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

        String text = String.format(ProjectManagementText.BLANK_INFO_PANEL, projectName, "Local", lastRevision);
        this.view.setInfoText(text);
    }
}
