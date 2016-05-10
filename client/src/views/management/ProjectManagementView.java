package views.management;

import java.awt.*;
import javax.swing.*;
import models.project.Diagram;
import constants.GUI;
import controllers.management.ProjectManagementController;

public class ProjectManagementView extends JFrame {
    private ProjectManagementController controller = new ProjectManagementController(this);
    private JComboBox<Diagram> projectChooser;
    private JTextPane infoPanel;

    public ProjectManagementView() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.render();
    }

    /**
     * Render the main window and launch functions to build its' components.
     */
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

        JButton openRecentDiagram = new JButton(GUI.ProjectManagement.OPEN_RECENT_BUTTON);
        openRecentDiagram.addActionListener(e -> controller.openRecentDiagram());

        JButton openProject = new JButton(GUI.ProjectManagement.OPEN_PROJECT_BUTTON);
        openProject.addActionListener(e -> controller.openProjects());

        JButton rename = new JButton(GUI.ProjectManagement.RENAME_BUTTON);
        rename.addActionListener(e -> controller.moveProject());

        buttons.add(create);
        buttons.add(openRecentDiagram);
        buttons.add(openProject);
        buttons.add(rename);

        this.add(buttons, BorderLayout.NORTH);
    }

    private void createChooserPanel() {
        JPanel chooserPanel = new JPanel();
        chooserPanel.setLayout(new BorderLayout());

//        Vector<Diagram> recentDiagrams = new Vector<>(RecentProjects.getRecentProjects());
//        Collections.reverse(recentDiagrams);

        this.projectChooser = new JComboBox<>();
//        this.projectChooser.setModel(new DefaultComboBoxModel(recentDiagrams));

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

    /**
     * Fetch the selected diagram
     * @return The selected diagram
     */
    public Diagram getSelectedProject() {
        return (Diagram) this.projectChooser.getSelectedItem();
    }

    /**
     * Sets the information from the selected diagram in the info panel
     * @param infoText The selected diagram's information
     */
    public void setInfoText(String infoText) {
        this.infoPanel.setText(infoText);
    }
}
