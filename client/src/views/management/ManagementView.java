package views.management;

import constants.GUI;
import controllers.management.ManagementController;

import javax.swing.*;
import java.awt.*;

public class ManagementView extends JFrame {
    private final ManagementController controller;

    public ManagementView() {
        this.controller = new ManagementController(this);
        this.setTitle("CreaTikZ - Manage your projects");
        this.setSize(new Dimension(1200, 600));

        getContentPane().setLayout(new BorderLayout());

        this.render();
        this.setVisible(true);
    }

    private void createFirstButtonsPanel() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        JButton create = new JButton(GUI.ProjectManagement.CREATE_BUTTON);
        create.addActionListener(e -> controller.createProject());
        buttons.add(create);

        JButton openProject = new JButton(GUI.ProjectManagement.OPEN_PROJECT_BUTTON);
        openProject.addActionListener(e -> controller.openProject());
        buttons.add(openProject);

        this.add(buttons, BorderLayout.NORTH);
    }

    public void render() {
        this.createFirstButtonsPanel();

        ProjectManagementView pmv = new ProjectManagementView(this);
        CloudManagementView cmv = new CloudManagementView(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        this.add(panel, BorderLayout.CENTER);

        panel.add(pmv);
        panel.add(cmv);

    }
}
