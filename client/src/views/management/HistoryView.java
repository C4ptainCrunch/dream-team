package views.management;

import java.awt.*;

import javax.swing.*;

import models.project.Diagram;
import constants.GUI.ProjectManagement;
import controllers.management.HistoryController;

/**
 * Implementation of the View (from the MVC architectural pattern) for the
 * History. The History keeps track of every modifications done to the current
 * diagram. This class opens a new window that displays the history.
 */
public class HistoryView extends JDialog {
    private final HistoryController controller;
    private final JTextPane historyPane = new JTextPane();
    private final JScrollPane scroll = new JScrollPane(getHistoryPane());
    private Diagram diagram;

    /**
     * Constructs a new view for the History with a given Diagram
     *
     * @param diagram
     *            The diagram
     */
    public HistoryView(Diagram diagram) {
        this.diagram = diagram;
        controller = new HistoryController(this, diagram);

        this.setTitle(ProjectManagement.DIFF_TEXT);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(scroll, BorderLayout.CENTER);
        this.setVisible(true);

        controller.fillView();

    }

    /**
     * Getter for the history pane where the history is displayed
     *
     * @return The history text panel
     */
    public JTextPane getHistoryPane() {
        return historyPane;
    }
}
