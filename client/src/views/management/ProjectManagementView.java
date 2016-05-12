package views.management;

import java.awt.*;
import java.util.Collections;
import java.util.Vector;

import javax.swing.*;

import models.project.Project;
import utils.RecentProjects;
import constants.GUI;
import controllers.management.ProjectManagementController;

/** TODO: Change this JavaDoc, it is outdated
 * JDialog that serves as a "Main Menu". From here, the users can create a new diagram, open a project,
 * move/rename a project and open a recently edited project.
 */
public class ProjectManagementView extends JPanel {
    private ProjectManagementController controller = new ProjectManagementController(this);
    private JList<Project> projectChooser;
    private JTextPane infoPanel;
    private JPanel buttons;
    private JPanel chooserPanel;
    private ManagementView parentView;

    public ProjectManagementView(ManagementView parentView) {
        this.parentView = parentView;
        this.render();
    }

    /**
     * Render the main window and launch functions to build its' components.
     */
    public final void render() {
        this.setPreferredSize(new Dimension(600, 300));
        this.setSize(900, 200);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        createInfoPanel();
        createChooserPanel();
        this.add(this.chooserPanel);
        this.add(this.infoPanel);
        createButtonsPanel();
    }

    private void createChooserPanel() {
        this.chooserPanel = new JPanel();
        this.chooserPanel.setPreferredSize(new Dimension(300,100));
        this.chooserPanel.setLayout(new BorderLayout());

        Vector<Project> recentProjects = new Vector<>(RecentProjects.getRecentProjects());
        Collections.reverse(recentProjects);

        this.projectChooser = new JList<>(recentProjects);
        this.projectChooser.addListSelectionListener(e -> controller.dropdownSelected(projectChooser.getSelectedValue()));
        this.projectChooser.setSelectedIndex(0);

        this.chooserPanel.add(new JLabel(GUI.ProjectManagement.DROPDOWN_HEADER), BorderLayout.NORTH);

        JScrollPane listScroller = new JScrollPane(this.projectChooser);
        this.chooserPanel.add(listScroller, BorderLayout.CENTER);

    }

    private void createInfoPanel() {
        this.infoPanel = new JTextPane();
        this.infoPanel.setOpaque(false);
        this.infoPanel.setEnabled(false);
        this.infoPanel.setPreferredSize(new Dimension(100,50));
    }

    private void createButtonsPanel() {
        this.buttons = new JPanel();
        this.buttons.setLayout(new BoxLayout(this.buttons, BoxLayout.X_AXIS));

        JButton openRecentProject = new JButton(GUI.ProjectManagement.OPEN_RECENT_BUTTON);
        openRecentProject.addActionListener(e -> controller.openRecentProject());
        this.buttons.add(openRecentProject);

        JButton rename = new JButton(GUI.ProjectManagement.RENAME_BUTTON);
        rename.addActionListener(e -> controller.moveProject());
        this.buttons.add(rename);

        JButton upload = new JButton("Upload selected project");
        // TODO : use constant
        upload.addActionListener(e -> controller.uploadProject());
        this.buttons.add(upload);
        this.add(this.buttons);

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

    /**
     * Dispose of the parent view
     */
    public void dispose() {
        this.parentView.dispose();
    }
}
