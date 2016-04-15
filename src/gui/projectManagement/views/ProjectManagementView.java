package gui.projectManagement.views;

import gui.projectManagement.controllers.ProjectManagementController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProjectManagementView extends JFrame implements ActionListener {
    private ProjectManagementController controller = new ProjectManagementController(this);
    private JComboBox<String> listSavedProjects;
    private JTextPane textInfo;

    public ProjectManagementView(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.render();
    }

    public void render() {
        this.setTitle("Project");
        this.setLayout(new BorderLayout());

        initButtonsPanel();
        initSavedProjectsPanel();
        initInfoPanel();
    }

    public String getSelectedPath(){
        return this.listSavedProjects.getSelectedItem().toString();
    }

    public int getSelectedPathIndex() { return this.listSavedProjects.getSelectedIndex(); }

    private String[] importSavedPaths(){
        return(controller.getStringListSavedPaths());
    }

    private void initSavedProjectsPanel(){
        String[] data = importSavedPaths();

        this.listSavedProjects = new JComboBox<>();
        this.listSavedProjects.setModel(new DefaultComboBoxModel(data));

        this.listSavedProjects.addActionListener(event -> updateInfoPanel());

        this.add(listSavedProjects, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    public void updateComboBox(String newDir){
        int index = getSelectedPathIndex();
        this.listSavedProjects.removeItem(this.listSavedProjects.getSelectedItem());
        this.listSavedProjects.insertItemAt(newDir,index);
        this.listSavedProjects.setSelectedIndex(index);
        this.revalidate();
    }

    private void initButtonsPanel(){
        JPanel buttons = new JPanel();
        JButton create = new JButton("Create Project");
        create.setActionCommand("create");

        JButton importFrom = new JButton("Import");
        importFrom.setActionCommand("import");

        JButton rename = new JButton("Rename");
        rename.setActionCommand("rename");

        create.addActionListener(this);
        importFrom.addActionListener(this);
        rename.addActionListener(this);

        buttons.add(create);
        buttons.add(importFrom);
        buttons.add(rename);

        buttons.setOpaque(true);
        this.add(buttons,BorderLayout.NORTH);
        this.pack();
        this.setVisible(true);
    }

    private void initInfoPanel(){
        JPanel infoPanel = new JPanel();
        this.textInfo = new JTextPane();
        this.textInfo.setText("INFORMATION ABOUT SELECTED PROJECT:\nProject Name: \nUser: Local\nLast revision:\n");

        infoPanel.add(this.textInfo);
        this.add(this.textInfo,BorderLayout.EAST);
        this.pack();
        this.setVisible(true);
    }

    public void updateInfoPanel(){
        String selectedProject = this.listSavedProjects.getSelectedItem().toString();
        int endIndex = selectedProject.lastIndexOf("/");
        String projectName = selectedProject.substring(endIndex+1, selectedProject.length());
        String lastRevision = this.controller.getLastRevision();

        this.textInfo.setText("INFORMATION ABOUT SELECTED PROJECT:\nProject Name: "+projectName+
                      "\nUser: Local\nLast revision: "+ lastRevision+"\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "create":
                controller.createProject();
                break;
            case "import":
                controller.importProject();
                break;
            case "rename":
                controller.renameProject();
                break;
            default:
                break;
        }

    }
}
