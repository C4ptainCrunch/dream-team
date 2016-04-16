package gui.projectManagement.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import constants.GUI.ProjectManagementText;
import gui.projectManagement.controllers.ProjectManagementController;

public class ProjectManagementView extends JFrame implements ActionListener {
    private final ProjectManagementController controller = new ProjectManagementController(this);
    private JComboBox<String> listSavedProjects;
    private JTextPane textInfo;

    public ProjectManagementView() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.render();
    }

    public void render() {
        this.setTitle("Project");
        this.setLayout(new BorderLayout());

        initButtonsPanel();
        initSavedProjectsPanel();
        initInfoPanel();
    }

    public String getSelectedPath() {
        return this.listSavedProjects.getSelectedItem().toString();
    }

    public int getSelectedPathIndex() {
        return this.listSavedProjects.getSelectedIndex();
    }

    private String[] importSavedPaths() {
        return controller.getStringListSavedPaths();
    }

    private void initSavedProjectsPanel() {
        String[] data = importSavedPaths();

        this.listSavedProjects = new JComboBox<>();
        this.listSavedProjects.setModel(new DefaultComboBoxModel(data));

        this.listSavedProjects.addActionListener(e -> controller.dropdownSelected(e));

        this.add(listSavedProjects, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    public void updateComboBox(String newDir) {
        int index = getSelectedPathIndex();
        this.listSavedProjects.removeItem(this.listSavedProjects.getSelectedItem());
        this.listSavedProjects.insertItemAt(newDir, index);
        this.listSavedProjects.setSelectedIndex(index);
        this.revalidate();
    }

    private void initButtonsPanel() {
        JPanel buttons = new JPanel();
        JButton create = new JButton("Create Project");
        create.setActionCommand("create");

        JButton importFrom = new JButton("Import");
        importFrom.setActionCommand("import");

        JButton rename = new JButton("Rename");
        rename.setActionCommand("rename");

        create.addActionListener(this);
        importFrom.addActionListener(this);
        rename.addActionListener(this);

        buttons.add(create);
        buttons.add(importFrom);
        buttons.add(rename);

        buttons.setOpaque(true);
        this.add(buttons, BorderLayout.NORTH);
        this.pack();
        this.setVisible(true);
    }

    private void initInfoPanel() {
        JPanel infoPanel = new JPanel();
        this.textInfo = new JTextPane();
        this.textInfo.setText(String.format(ProjectManagementText.BLANK_INFO_PANEL, "", "Local", ""));

        infoPanel.add(this.textInfo);
        this.add(this.textInfo, BorderLayout.EAST);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
        case "create":
            controller.createProject();
            break;
        case "import":
            controller.importProject();
            break;
        case "rename":
            controller.renameProject();
            break;
        default:
            break;
        }

    }

    public void setInfoText(String infoText) {
        this.textInfo.setText(infoText);
    }
}
