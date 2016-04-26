package views.management;

import java.awt.*;
import java.util.Collections;
import java.util.Vector;

import javax.swing.*;

import models.project.Project;
import models.project.RecentProjects;
import constants.GUI;
import controllers.management.ProjectManagementController;

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
        this.setPreferredSize(new Dimension(900, 200));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        createButtonsPanel();
        createInfoPanel();
        createChooserPanel();

        this.pack();
        this.setVisible(true);
    }

    private void createButtonsPanel() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        JButton create = new JButton(GUI.ProjectManagement.CREATE_BUTTON);
        create.addActionListener(e -> controller.createProject());

        JButton open = new JButton(GUI.ProjectManagement.OPEN_BUTTON);
        open.addActionListener(e -> controller.openProject());

        JButton rename = new JButton(GUI.ProjectManagement.RENAME_BUTTON);
        rename.addActionListener(e -> controller.renameProject());

        buttons.add(create);
        buttons.add(open);
        buttons.add(rename);

        this.add(buttons, BorderLayout.NORTH);
    }

    private void createChooserPanel() {
        JPanel chooserPanel = new JPanel();
        chooserPanel.setLayout(new BorderLayout());

        Vector<Project> recentProjects = new Vector<>(RecentProjects.getRecentProjects());
        Collections.reverse(recentProjects);

        this.projectChooser = new JComboBox<>();
        this.projectChooser.setModel(new DefaultComboBoxModel(recentProjects));

        this.projectChooser.addActionListener(e -> controller.dropdownSelected((JComboBox) e.getSource()));
        controller.dropdownSelected(this.projectChooser);

        chooserPanel.add(new JLabel(GUI.ProjectManagement.DROPDOWN_HEADER), BorderLayout.NORTH);
        chooserPanel.add(this.projectChooser, BorderLayout.CENTER);

        this.add(chooserPanel, BorderLayout.CENTER);

    }

    private void createInfoPanel() {
        JPanel infoPanel = new JPanel();
        this.infoPanel = new JTextPane();
        this.setInfoText("                                                        ");

        infoPanel.add(this.infoPanel);
        this.add(this.infoPanel, BorderLayout.EAST);
    }

    public Project getSelectedProject() {
        return (Project) this.projectChooser.getSelectedItem();
    }

    public void setInfoText(String infoText) {
        this.infoPanel.setText(infoText);
    }
}
