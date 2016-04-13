package gui.projectManagement.views;

import gui.projectManagement.controllers.ProjectManagementController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        this.listSavedProjects = new JComboBox<>();
        this.listSavedProjects.setModel(new DefaultComboBoxModel(data));

        this.add(listSavedProjects, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }

    public void updateComboBox(String newDir){
        int index = this.listSavedProjects.getSelectedIndex();
        System.out.println(this.listSavedProjects.getSelectedItem());
        this.listSavedProjects.removeItem(this.listSavedProjects.getSelectedItem());
        this.listSavedProjects.insertItemAt(newDir,index);
        this.listSavedProjects.setSelectedIndex(index);
        this.render();
    }

    private void initButtonsPanel(){
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
            case "rename":
                controller.renameProject();
                break;
            default:
                break;
        }

    }
}
