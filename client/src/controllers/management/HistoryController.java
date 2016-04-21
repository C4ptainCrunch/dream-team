package controllers.management;

import java.awt.*;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import models.project.Diff;
import models.project.Project;
import utils.Log;
import views.management.HistoryView;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the
 * History. The History keeps track of every modifications done to the current
 * project. This class fills the history window with the modifications
 */
public class HistoryController {
    private final static Logger logger = Log.getLogger(HistoryController.class);
    private final HistoryView view;
    private final Project project;

    /**
     * Constructs a new controller for the History, with a given Historyview and
     * Project
     *
     * @param view
     *            The HistoryView which is associated with this controller
     * @param project
     *            The project
     */
    public HistoryController(HistoryView view, Project project) {
        this.project = project;
        this.view = view;
    }

    /**
     * Decides which color to apply whether the given string matches a certain
     * pattern. Green for addition Red for deletion Black for not a modification
     * (applied when a date is encountered)
     *
     * @param str
     *            The string
     * @return Color The color
     */
    private static Color colorHelper(String str) {
        if (str.isEmpty()) {
            return Color.BLACK;
        }
        if (Pattern.matches("^\\d{4}\\/\\d\\d\\/\\d\\d \\d\\d:\\d\\d:\\d\\d$", str)) {
            return Color.BLACK;
        } else if (str.charAt(0) == '+') {
            return Color.GREEN;
        } else if (str.charAt(0) == '-') {
            return Color.RED;
        }
        return Color.BLACK;
    }

    /**
     * Fills the view with the modifications text found in the history file
     */
    public void fillView() {
        try {
            for (Diff diff : project.getDiffs()) {
                this.appendString(diff.getDate().toString() + "\n", Color.green);

                for (String s : diff.getPatch().split(System.getProperty("line.separator"))) {
                    Color color = colorHelper(s);
                    this.appendString(s + "\n", color);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.severe("Error while reading diff file: " + e.toString());
        }
    }

    /**
     * Appends a string with a certain color in the view's history text This is
     * used to differentiate whether the modification is an addition, deletion
     * or not a modification in the view
     *
     * @param str
     *            The string to be colored
     * @param color
     *            The color
     */
    private void appendString(String str, Color color) {
        StyledDocument document = (StyledDocument) view.getHistoryPane().getDocument();
        Style style = document.addStyle("color", null);
        StyleConstants.setForeground(style, color);
        try {
            document.insertString(document.getLength(), str, style);
        } catch (BadLocationException e) {
            e.getStackTrace();
        }
    }

}
