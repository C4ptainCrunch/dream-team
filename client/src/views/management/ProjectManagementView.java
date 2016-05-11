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
    private JPanel firstButtons;
    private JPanel secondButtons;
    private JPanel chooserPanel;

    public ProjectManagementView() {
        this.render();
    }

    /**
     * Render the main window and launch functions to build its' components.
     */
    public final void render() {
        this.setTitle("TikzCreator : choose a project");
        this.setPreferredSize(new Dimension(600, 300));
        this.setSize(900, 200);
        this.setLocationRelativeTo(null);
        getContentPane().setLayout(
                new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)
        );

        createFirstButtonsPanel();
        createInfoPanel();
        createChooserPanel();
        this.add(this.chooserPanel);
        this.add(this.infoPanel);

        createSecondButtonsPanel();

        this.pack();
        this.setVisible(true);
    }

    private void createFirstButtonsPanel() {
        this.firstButtons = new JPanel();
        this.firstButtons.setLayout(new BoxLayout(this.firstButtons, BoxLayout.X_AXIS));

        JButton create = new JButton(GUI.ProjectManagement.CREATE_BUTTON);
        create.addActionListener(e -> controller.createProject());

        JButton openProject = new JButton(GUI.ProjectManagement.OPEN_PROJECT_BUTTON);
        openProject.addActionListener(e -> controller.openProjects());

        this.firstButtons.add(create);
        this.firstButtons.add(openProject);

        this.add(this.firstButtons);
    }

    private void createSecondButtonsPanel() {
        this.secondButtons = new JPanel();
        this.secondButtons.setLayout(new BoxLayout(this.secondButtons, BoxLayout.X_AXIS));

        JButton openRecentProject = new JButton(GUI.ProjectManagement.OPEN_RECENT_BUTTON);
        openRecentProject.addActionListener(e -> controller.openRecentProject());

        JButton rename = new JButton(GUI.ProjectManagement.RENAME_BUTTON);
        rename.addActionListener(e -> controller.moveProject());

        this.secondButtons.add(rename);
        this.secondButtons.add(openRecentProject);

        this.add(this.secondButtons);

    }

    private void createChooserPanel() {
        this.chooserPanel = new JPanel();
        this.chooserPanel.setLayout(new BorderLayout());

        Vector<Project> recentProjects = new Vector<>(RecentProjects.getRecentProjects());
        Collections.reverse(recentProjects);

        this.projectChooser = new JList<>(recentProjects);
        this.projectChooser.setModel(new DefaultComboBoxModel(recentProjects));

        this.projectChooser.addListSelectionListener(e -> controller.dropdownSelected(projectChooser.getSelectedValue()));
        this.projectChooser.setSelectedIndex(0);

        this.chooserPanel.add(new JLabel(GUI.ProjectManagement.DROPDOWN_HEADER), BorderLayout.NORTH);

        JScrollPane listScroller = new JScrollPane(this.projectChooser);
        this.chooserPanel.add(listScroller, BorderLayout.CENTER);

    }

    private void createInfoPanel() {
        this.infoPanel = new JTextPane();
        this.infoPanel.setOpaque(false);
        this.setInfoText("                                                        ");
        this.infoPanel.setPreferredSize(new Dimension(100,100));
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
