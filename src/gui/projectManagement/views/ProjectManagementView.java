package gui.projectManagement.views;

import gui.projectManagement.controllers.ProjectManagementController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectManagementView extends JFrame implements ActionListener {
    private ProjectManagementController controller = new ProjectManagementController(this);

    public ProjectManagementView(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.render();
    }

    public void render() {
        this.setTitle("Project");

        initButtonsPanel();

        /*
        bv.setOpaque(true);
        this.setContentPane(bv);
        this.pack();
        this.setVisible(true);
        */
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
        this.setContentPane(buttons);
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
