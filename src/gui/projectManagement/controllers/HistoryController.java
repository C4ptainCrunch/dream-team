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
import models.Project;

public class HistoryController {
    private final HistoryView view;
    private final Project project;
    private Color currentColor = Color.BLACK;

    public HistoryController(HistoryView view, Project project) {
        this.project = project;
        this.view = view;
    }

    public void fillView() {
        try {
            Files.lines(project.getDiffPath(), Charset.defaultCharset()).forEach(line -> {
                colorHelper(line);
                appendString(line + "\n", currentColor);
            });
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

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
