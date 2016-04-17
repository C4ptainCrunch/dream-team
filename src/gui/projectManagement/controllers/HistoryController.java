package gui.projectManagement.controllers;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import gui.projectManagement.views.HistoryView;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the History.
 * The History keeps track of every modifications done to the current project.
 * This class fills the history window with the modifications
 */
public class HistoryController {
    private final HistoryView view;
    private final String path; // TODO acaccia : could we use EditorView.getProjectPath() here ?
    private Color currentColor = Color.BLACK;

    /**
     * Constructs a new controller for the History,
     * with a given Historyview and a path to the history file
     * @param view The HistoryView which is associated with this controller
     * @param path The history file path
     */
    public HistoryController(HistoryView view, String path) {
        this.path = path;
        this.view = view;
    }

    /**
     * Fills the view with the modifications
     * text found in the history file
     */
    public void fillView() {
        try {
            Files.lines(Paths.get(path + "/diffs"), Charset.defaultCharset()).forEach(line -> {
                colorHelper(line);
                appendString(line + "\n", currentColor);
            });
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    /**
     * Appends a string with a certain color in the view's history text
     * This is used to differentiate whether the modification is an
     * addition, deletion or not a modification in the view
     * @param str The string to be colored
     * @param color The color
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

    /**
     * Decides which color to apply whether the given
     * string matches a certain pattern.
     * Green for addition
     * Red for deletion
     * Black for not a modification (applied when a date is encountered)
     * @param str The string
     */
    private void colorHelper(String str) {
        if (str.isEmpty()) {
            return;
        }
        if (Pattern.matches("^\\d{4}\\/\\d\\d\\/\\d\\d \\d\\d:\\d\\d:\\d\\d$", str)) {
            currentColor = Color.BLACK;
        } else if (str.charAt(0) == '+') {
            currentColor = Color.GREEN;
        } else if (str.charAt(0) == '-') {
            currentColor = Color.RED;
        }
    }

}
