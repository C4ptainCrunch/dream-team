package views.management;

import controllers.management.UserPermissionWindowController;
import models.databaseModels.Project;

import javax.swing.*;
import java.awt.*;

public class UserPermissionWindowView extends JDialog {

    private final Project currentProject;
    private final String user;
    private final PermissionWindowView parentView;
    private UserPermissionWindowController controller;
    private JPanel mainPanel;
    private JPanel permsPanel;
    private JButton permsButton;

    public UserPermissionWindowView(PermissionWindowView parentView, Project currentProject, String user) {
        this.controller = new UserPermissionWindowController(this);
        this.currentProject = currentProject;
        this.user = user;
        this.parentView = parentView;
        this.setSize(new Dimension(400, 300));
        this.render();
        this.add(this.mainPanel);
        this.setVisible(true);
    }

    private void render() {
        this.setTitle("TikzCreator : set permissions for "+ this.user);
        this.setLocationRelativeTo(null);
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        createPermsPanel();
        this.mainPanel.add(this.permsPanel);
    }

    private void createPermsPanel() {
        this.permsPanel = new JPanel();
        boolean readPerm = this.controller.getUserReadPerm();
        boolean writePerm = this.controller.getUserWritePerm();
        JCheckBox read = new JCheckBox("Read permission", readPerm);
        JCheckBox write = new JCheckBox("Write permission", writePerm);
        this.permsPanel.add(read);
        this.permsPanel.add(write);

        this.permsButton = new JButton("Set permissions for project");
        this.permsButton.addActionListener(
                e -> controller.setPermissions(
                        this.currentProject,
                        this.user,
                        read.isSelected(),
                        write.isSelected())
        );

        this.permsPanel.add(this.permsButton);
    }

    public void setParentViewVisible() {
        this.parentView.setVisible(true);
    }
}
