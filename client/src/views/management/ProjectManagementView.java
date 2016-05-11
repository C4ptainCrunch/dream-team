package views.management;

import java.awt.*;
import java.util.Collections;
import java.util.Vector;

import javax.swing.*;

import models.project.Project;
import utils.RecentProjects;
import constants.GUI;
import controllers.management.ProjectManagementController;

public class ProjectManagementView extends JDialog {
    private ProjectManagementController controller = new ProjectManagementController(this);
    private JList<Project> projectChooser;
    private JTextPane infoPanel;

    public ProjectManagementView() {
        this.render();
    }

    /**
     * Render the main window and launch functions to build its' components.
     */
    public final void render() {
        this.setTitle("TikzCreator : choose a project");
        this.setPreferredSize(new Dimension(900, 200));
        this.setSize(900, 200);
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

        JButton openRecentProject = new JButton(GUI.ProjectManagement.OPEN_RECENT_BUTTON);
        openRecentProject.addActionListener(e -> controller.openRecentProject());

        JButton openProject = new JButton(GUI.ProjectManagement.OPEN_PROJECT_BUTTON);
        openProject.addActionListener(e -> controller.openProjects());

        JButton rename = new JButton(GUI.ProjectManagement.RENAME_BUTTON);
        rename.addActionListener(e -> controller.moveProject());

        buttons.add(create);
        buttons.add(openRecentProject);
        buttons.add(openProject);
        buttons.add(rename);

        this.add(buttons, BorderLayout.NORTH);
    }

    private void createChooserPanel() {
        JPanel chooserPanel = new JPanel();
        chooserPanel.setLayout(new BorderLayout());

        Vector<Project> recentProjects = new Vector<>(RecentProjects.getRecentProjects());
        Collections.reverse(recentProjects);

        this.projectChooser = new JList<>(recentProjects);
        this.projectChooser.setModel(new DefaultComboBoxModel(recentProjects));

        this.projectChooser.addListSelectionListener(e -> controller.dropdownSelected(projectChooser.getSelectedValue()));
        this.projectChooser.setSelectedIndex(0);

        chooserPanel.add(new JLabel(GUI.ProjectManagement.DROPDOWN_HEADER), BorderLayout.NORTH);

        JScrollPane listScroller = new JScrollPane(this.projectChooser);
        chooserPanel.add(listScroller, BorderLayout.CENTER);

        this.add(chooserPanel, BorderLayout.CENTER);

    }

    private void createInfoPanel() {
        JPanel infoPanel = new JPanel();
        this.infoPanel = new JTextPane();
        this.setInfoText("                                                        ");

        infoPanel.add(this.infoPanel);
        this.add(this.infoPanel, BorderLayout.EAST);
    }

    /**
     * Fetch the selected project
     * @return The selected project
     */
    public Project getSelectedProject() {
        return (Project) this.projectChooser.getSelectedValue();
    }

    /**
     * Sets the information from the selected project in the info panel
     * @param infoText The selected project's information
     */
    public void setInfoText(String infoText) {
        this.infoPanel.setText(infoText);
    }
}
