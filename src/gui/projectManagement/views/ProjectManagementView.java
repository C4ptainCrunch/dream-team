package gui.projectManagement.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import constants.GUI;
import constants.GUI.ProjectManagement;
import gui.projectManagement.controllers.ProjectManagementController;

public class ProjectManagementView extends JFrame {
    private ProjectManagementController controller = new ProjectManagementController(this);
    private JComboBox<String> listSavedProjects;
    private JTextPane textInfo;

    public ProjectManagementView() {

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

    public String getSelectedPath() {
        return this.listSavedProjects.getSelectedItem().toString();
    }

    public int getSelectedPathIndex() {
        return this.listSavedProjects.getSelectedIndex();
    }

    private String[] importSavedPaths() {
        return (controller.getStringListSavedPaths());
    }

    private void initSavedProjectsPanel() {
        String[] data = importSavedPaths();

        this.listSavedProjects = new JComboBox<>();
        this.listSavedProjects.setModel(new DefaultComboBoxModel(data));

        this.listSavedProjects.addActionListener(e -> controller.dropdownSelected(e));

        this.add(listSavedProjects, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    public void updateComboBox(String newDir) {
        int index = getSelectedPathIndex();
        this.listSavedProjects.removeItem(this.listSavedProjects.getSelectedItem());
        this.listSavedProjects.insertItemAt(newDir, index);
        this.listSavedProjects.setSelectedIndex(index);
        this.revalidate();
    }

    private void initButtonsPanel() {
        JPanel buttons = new JPanel();

        JButton create = new JButton(GUI.ProjectManagement.CREATE_BUTTON);
        create.addActionListener(e -> controller.createProject());

        JButton open = new JButton(GUI.ProjectManagement.OPEN_BUTTON);
        open.addActionListener(e -> controller.importProject());

        JButton rename = new JButton(GUI.ProjectManagement.RENAME_BUTTON);
        rename.addActionListener(e -> controller.renameProject());

        buttons.add(create);
        buttons.add(open);
        buttons.add(rename);

        buttons.setOpaque(true);
        this.add(buttons, BorderLayout.NORTH);
        this.pack();
        this.setVisible(true);
    }

    private void initInfoPanel() {
        JPanel infoPanel = new JPanel();
        this.textInfo = new JTextPane();
        this.textInfo.setText(String.format(ProjectManagement.BLANK_INFO_PANEL, "", "Local", ""));

        infoPanel.add(this.textInfo);
        this.add(this.textInfo, BorderLayout.EAST);
        this.pack();
        this.setVisible(true);
    }

    public void setInfoText(String infoText) {
        this.textInfo.setText(infoText);
    }
}
