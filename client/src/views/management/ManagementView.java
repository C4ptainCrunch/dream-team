package views.management;

import constants.GUI;
import controllers.management.ManagementController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagementView extends JFrame {
    private final ManagementController controller;
    ProjectManagementView pmv;
    CloudManagementView cmv;

    public ManagementView() {
        this.controller = new ManagementController(this);
        this.setTitle("CreaTikZ - Manage your projects");
        this.setSize(new Dimension(1200, 600));

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        this.render();
        this.setVisible(true);
    }

    private void addMenubar() {
        JMenuBar bar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        bar.add(fileMenu);

        JMenuItem create = new JMenuItem(GUI.ProjectManagement.CREATE_BUTTON);
        create.addActionListener(actionEvent -> controller.createProject());
        fileMenu.add(create);

        JMenuItem open = new JMenuItem(GUI.ProjectManagement.OPEN_PROJECT_BUTTON);
        open.addActionListener(actionEvent -> controller.openProject());
        fileMenu.add(open);

        this.setJMenuBar(bar);

        JMenu userMenu = new JMenu("User");
        bar.add(userMenu);

        JMenuItem edit = new JMenuItem("Edit profile");
        edit.addActionListener(actionEvent -> controller.editProfile());
        userMenu.add(edit);

        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(actionEvent -> controller.logout());
        userMenu.add(logout);

        this.setJMenuBar(bar);

    }

    public void render() {
        this.addMenubar();

        this.pmv = new ProjectManagementView(this);
        this.cmv = new CloudManagementView(this);

        this.add(pmv);
        this.add(cmv);

    }

    public void refresh() {
        this.pmv.refresh();
        this.cmv.refresh();
    }
}
