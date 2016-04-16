package gui.projectManagement.views;

import java.awt.*;

import javax.swing.*;

import constants.GUI.ProjectManagement;
import gui.projectManagement.controllers.HistoryController;
import models.Project;

public class HistoryView extends JFrame {
    private final HistoryController controller;
    private Project project;
    private final JTextPane historyPane = new JTextPane();
    private final JScrollPane scroll = new JScrollPane(getHistoryPane());

    public HistoryView(Project project) {
        this.project = project;
        controller = new HistoryController(this, project);

        this.setTitle(ProjectManagement.DIFF_TEXT);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(scroll, BorderLayout.CENTER);
        this.setVisible(true);

        controller.fillView();

    }

    public JTextPane getHistoryPane() {
        return historyPane;
    }
}
