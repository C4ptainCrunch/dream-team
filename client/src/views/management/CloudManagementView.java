package views.management;

import constants.GUI;
import controllers.management.CloudManagementController;
import misc.utils.CloudHelper;
import models.project.Project;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Vector;

public class CloudManagementView extends JPanel {

    private ManagementView parentView;
    private CloudManagementController controller = new CloudManagementController();
    private JList<String> projectChooser;
    private JTextPane infoPanel;
    private JPanel chooserPanel;
    private JPanel buttons;


    public CloudManagementView(ManagementView parentView) {
        this.parentView = parentView;
        this.render();
    }

    public void render() {
        this.setPreferredSize(new Dimension(600, 300));
        this.setSize(900, 200);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        createInfoPanel();
        createChooserPanel();
        this.add(this.chooserPanel);
        this.add(this.infoPanel);
        createButtonsPanel();
        this.add(this.buttons);
    }

    private void createChooserPanel() {
        //TODO: Use constants!
        this.chooserPanel = new JPanel();
        this.chooserPanel.setPreferredSize(new Dimension(300,100));
        this.chooserPanel.setLayout(new BorderLayout());

        Vector<String> sharedProjects = CloudHelper.getSharedProjects();

        this.projectChooser = new JList<>(sharedProjects);
        this.projectChooser.addListSelectionListener(e -> controller.dropdownSelected(projectChooser.getSelectedValue()));
        this.projectChooser.setSelectedIndex(0);

        this.chooserPanel.add(new JLabel("Choose a project that you shared or has been shared with you"), BorderLayout.NORTH);

        JScrollPane listScroller = new JScrollPane(this.projectChooser);
        this.chooserPanel.add(listScroller, BorderLayout.CENTER);

    }

    private void createInfoPanel() {
        this.infoPanel = new JTextPane();
        this.infoPanel.setOpaque(false);
        this.infoPanel.setEnabled(false);
        this.infoPanel.setPreferredSize(new Dimension(100,100));
    }

    private void createButtonsPanel() {
        // TODO: Use constants!
        this.buttons = new JPanel();
        this.buttons.setLayout(new BoxLayout(this.buttons, BoxLayout.X_AXIS));

        JButton openSharedProject = new JButton("Open shared project");
        openSharedProject.addActionListener(e -> controller.openSharedProject());
        this.buttons.add(openSharedProject);

        JButton setPermissions = new JButton("Set permissions");
        setPermissions.addActionListener(e -> controller.setPermissionsToSelectedProject());
        this.buttons.add(setPermissions);
    }

    public void dispose() {
        this.parentView.dispose();
    }

}
