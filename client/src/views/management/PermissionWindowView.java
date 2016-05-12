package views.management;


import controllers.management.PermissionWindowController;
import models.databaseModels.Project;

import javax.swing.*;
import java.awt.*;

public class PermissionWindowView extends JDialog {

    private PermissionWindowController controller;
    private Project project;
    private JPanel mainPanel;
    private JPanel defaultPermsPanel;
    private JPanel usersSelectPanel;
    private JList usersList;
    private JButton defaultPermsButton;
    private JButton userPermsButton;

    public PermissionWindowView(Project project) {
        this.controller = new PermissionWindowController(this);
        this.project = project;
        this.setSize(new Dimension(400, 300));
        this.render();
        this.setVisible(true);
    }

    private void render() {
        this.setTitle("TikzCreator : set permissions to your shared projects");
        this.setLocationRelativeTo(null);
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        createUsersSelectionPanel();
        createDefaultPermsPanel();
        this.mainPanel.add(this.defaultPermsPanel);
        this.mainPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        this.mainPanel.add(this.usersSelectPanel);
        this.add(this.mainPanel);
    }

    private void createDefaultPermsPanel() {
        this.defaultPermsPanel = new JPanel();
        boolean defaultReadPerm = this.controller.getDefaultReadPerm();
        boolean defaultWritePerm = this.controller.getDefaultWritePerm();
        JCheckBox defaultRead = new JCheckBox("Default read", defaultReadPerm);
        JCheckBox defaultWrite = new JCheckBox("Default write", defaultWritePerm);
        this.defaultPermsPanel.add(defaultRead);
        this.defaultPermsPanel.add(defaultWrite);

        this.defaultPermsButton = new JButton("Set default perms");
        this.defaultPermsButton.addActionListener(
                e -> controller.setDefaultPermissions(
                        this.project,
                        defaultRead.isSelected(),
                        defaultWrite.isSelected())
                );

        this.defaultPermsPanel.add(this.defaultPermsButton);
    }


    private void createUsersSelectionPanel() {
        this.usersSelectPanel = new JPanel();

        this.usersList = new JList(this.controller.getServerUsers());
        this.usersList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.usersList.setLayoutOrientation(JList.VERTICAL);
        this.usersList.setVisibleRowCount(-1);
        this.usersList.setSelectedIndex(0);

        JScrollPane listScroller = new JScrollPane(this.usersList);
        listScroller.setPreferredSize(new Dimension(250, 130));
        this.usersSelectPanel.add(listScroller);

        this.userPermsButton = new JButton("Set perms for selected user");
        this.userPermsButton.addActionListener(
                e -> controller.setUserPermissions(this.project, this.usersList.getSelectedValue().toString())
        );

        this.usersSelectPanel.add(this.userPermsButton);

    }

}
