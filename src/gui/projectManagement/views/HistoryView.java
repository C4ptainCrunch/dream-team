package gui.projectManagement.views;

import java.awt.*;

import javax.swing.*;

import constants.GUI.ProjectManagementText;
import gui.projectManagement.controllers.HistoryController;

public class HistoryView extends JFrame {
    private HistoryController controller;
    private JTextPane historyPane = new JTextPane();
    private JScrollPane scroll = new JScrollPane(getHistoryPane());

    public HistoryView(String path) {
        controller = new HistoryController(this, path);

        this.setTitle(ProjectManagementText.DIFF_TEXT);
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
