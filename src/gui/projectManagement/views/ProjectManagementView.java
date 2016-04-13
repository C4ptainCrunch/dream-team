package gui.projectManagement.views;

import gui.projectManagement.controllers.ProjectManagementController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ProjectManagementView extends JFrame implements ActionListener {
    private ProjectManagementController controller = new ProjectManagementController(this);
    private JComboBox<String> listSavedProjects;

    public ProjectManagementView(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.render();
    }

    public void render() {
        this.setTitle("Project");
        this.setLayout(new BorderLayout());

        initButtonsPanel();
        initSavedProjectsPanel();

        /*
        bv.setOpaque(true);
        this.setContentPane(bv);
        this.pack();
        this.setVisible(true);
        */
    }

    public String getSelectedPath(){
        return this.listSavedProjects.getSelectedItem().toString();
    }

    private String[] importSavedPaths(){
        return(controller.getStringListSavedPaths());
    }

    private void initSavedProjectsPanel(){
        String[] data = importSavedPaths();

        this.listSavedProjects = new JComboBox<>(data);
        this.add(listSavedProjects, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }

    private void initButtonsPanel(){
        JPanel buttons = new JPanel();
        JButton create = new JButton("Create Project");
        create.setActionCommand("create");

        JButton importFrom = new JButton("Import");
        importFrom.setActionCommand("import");

        create.addActionListener(this);
        importFrom.addActionListener(this);

        buttons.add(create);
        buttons.add(importFrom);

        buttons.setOpaque(true);
        this.add(buttons,BorderLayout.NORTH);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "create":
                controller.createProject();
                break;
            case "import":
                controller.importProject();
                break;
            default:
                break;
        }

    }
}
