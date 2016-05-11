package views.management;

import constants.GUI;
import controllers.management.ManagementController;

import javax.swing.*;
import java.awt.*;

public class ManagementView extends JFrame {
    private final ManagementController controller;

    public ManagementView() {
        this.controller = new ManagementController(this);

        this.setTitle("Trololo");
        this.setSize(new Dimension(600, 600));

        getContentPane().setLayout(
                new BoxLayout(getContentPane(), BoxLayout.X_AXIS)
        );

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

        this.add(buttons);
    }

    public void render() {
        this.createFirstButtonsPanel();
        ProjectManagementView pmv = new ProjectManagementView(this);
        this.add(pmv);
        CloudManagementView cmv = new CloudManagementView(this);
        this.add(cmv);

    }
}
