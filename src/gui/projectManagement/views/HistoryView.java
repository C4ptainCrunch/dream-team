package gui.projectManagement.views;

import java.awt.*;

import javax.swing.*;

import constants.GUI.ProjectManagementText;
import gui.projectManagement.controllers.HistoryController;

/**
 * Implementation of the View (from the MVC architectural pattern) for the History.
 * The History keeps track of every modifications done to the current project.
 * This class opens a new window that displays the history.
 */
public class HistoryView extends JFrame {
    private final HistoryController controller;
    private final JTextPane historyPane = new JTextPane();
    private final JScrollPane scroll = new JScrollPane(getHistoryPane());

    /**
     * Constructs a new view for the History
     * with a given path to the history file
     * where the modifications are written
     * @param path The history file path
     */
    public HistoryView(String path) {
        controller = new HistoryController(this, path);

        this.setTitle(ProjectManagementText.DIFF_TEXT);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(scroll, BorderLayout.CENTER);
        this.setVisible(true);

        controller.fillView();

    }

    /**
     * Getter for the history pane where the history is displayed
     * @return The history text panel
     */
    public JTextPane getHistoryPane() {
        return historyPane;
    }
}
