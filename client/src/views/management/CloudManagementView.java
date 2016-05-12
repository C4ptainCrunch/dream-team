package views.management;

import controllers.management.CloudManagementController;
import misc.logic.CloudLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Panel that allows the user to see which projects he has shared or have
 * been shared with him. From here, he has several possible options to apply to these
 * projects.
 */
public class CloudManagementView extends JPanel {

    private ManagementView parentView;
    private CloudManagementController controller;
    private JList<models.databaseModels.Project> projectChooser;
    private JTextPane infoPanel;
    private JPanel chooserPanel;
    private JPanel buttons;

    /** Constructs the CloudManagementView
     * @param parentView The view from the parent
     */
    public CloudManagementView(ManagementView parentView) {
        this.controller = new CloudManagementController(this);
        this.parentView = parentView;
        this.render();
    }

    /**
     * Render the main window and launch functions to build its' components.
     */
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

        this.projectChooser = new JList<>(this.getListModel());
        this.projectChooser.addListSelectionListener(e -> controller.dropdownSelected(projectChooser.getSelectedValue()));
        this.projectChooser.setSelectedIndex(0);

        this.projectChooser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    controller.openSharedProject();
                }
            }
        });

        this.chooserPanel.add(new JLabel("Choose a project that you shared or has been shared with you"), BorderLayout.NORTH);

        JScrollPane listScroller = new JScrollPane(this.projectChooser);
        this.chooserPanel.add(listScroller, BorderLayout.CENTER);

    }

    private void createInfoPanel() {
        this.infoPanel = new JTextPane();
        this.infoPanel.setOpaque(false);
        this.infoPanel.setEnabled(false);
        this.infoPanel.setDisabledTextColor(Color.BLACK);
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

        JButton local = new JButton("Get local copy");
        local.addActionListener(e -> controller.getLocalCopy());
        this.buttons.add(local);
    }

    /**
     * Dispose of the parent view
     */
    public void dispose() {
        this.parentView.dispose();
    }

    public void refresh() {
        this.projectChooser.setModel(this.getListModel());
    }

    private DefaultListModel getListModel(){
        DefaultListModel model = new DefaultListModel();
        List<models.databaseModels.Project> projectsList = CloudLogic.getSharedProjects();
        if(projectsList != null) {
            projectsList.forEach(model::addElement);
        }
        return model;
    }

    /**
     * Sets the information from the selected project in the info panel
     * @param infoText The selected project's information
     */
    public void setInfoText(String infoText) { this.infoPanel.setText(infoText); }

    public models.databaseModels.Project getSelectedProject() {
        return this.projectChooser.getSelectedValue();
    }

    /**
     * Get the view from the parent
     * @return The parent view
     */
    public ManagementView getParentView() {
        return parentView;
    }
}
