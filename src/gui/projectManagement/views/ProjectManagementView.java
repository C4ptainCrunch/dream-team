package gui.projectManagement.views;

import java.awt.*;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import javax.swing.*;

import constants.GUI;
import constants.GUI.ProjectManagement;
import gui.projectManagement.controllers.ProjectManagementController;
import models.Project;
import models.tikz.RecentProjects;

public class ProjectManagementView extends JFrame {
    private ProjectManagementController controller = new ProjectManagementController(this);
    private JComboBox<Project> projectChooser;
    private JTextPane infoPanel;

    public ProjectManagementView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.render();
    }

    public final void render() {
        this.setTitle("TikzCreator : choose a project");
        this.setLayout(new BorderLayout());

        createButtonsPanel();
        createChooserPanel();
        createInfoPanel();
    }

    private void createButtonsPanel() {
        JPanel buttons = new JPanel();

        JButton create = new JButton(GUI.ProjectManagement.CREATE_BUTTON);
        create.addActionListener(e -> controller.createProject());

        JButton open = new JButton(GUI.ProjectManagement.OPEN_BUTTON);
//        open.addActionListener(e -> controller.openProject());

        JButton rename = new JButton(GUI.ProjectManagement.RENAME_BUTTON);
//        rename.addActionListener(e -> controller.renameProject());

        buttons.add(create);
        buttons.add(open);
        buttons.add(rename);

        buttons.setOpaque(true);
        this.add(buttons, BorderLayout.NORTH);
        this.pack();
        this.setVisible(true);
    }

    private void createChooserPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.add(panel, BorderLayout.CENTER);

        Vector<Project> recentProjects = null;
        try {
            recentProjects = RecentProjects.getRecentProjects();
            Collections.reverse(recentProjects);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.projectChooser = new JComboBox<>();
        this.projectChooser.setModel(new DefaultComboBoxModel(recentProjects));

        this.projectChooser.addActionListener(e -> controller.dropdownSelected(e));

        panel.add(new JLabel(GUI.ProjectManagement.DROPDOWN_HEADER));
        panel.add(projectChooser);

        this.pack();
        this.setVisible(true);
    }

    private void createInfoPanel() {
        JPanel infoPanel = new JPanel();
        this.infoPanel = new JTextPane();

        infoPanel.add(this.infoPanel);
        this.add(this.infoPanel, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }

    public void setInfoText(String infoText) {
        this.infoPanel.setText(infoText);
    }
}
