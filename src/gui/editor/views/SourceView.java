package gui.editor.views;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import models.TikzGraph;
import gui.editor.controllers.SourceController;

/**
 * Implementation of the View (from the MVC architectural pattern) for the Source.
 * The Source is the area of the GUI where the Tikz is edited.
 */
public class SourceView extends JPanel {
    private static final int TEXT_AREA_WIDTH = 300;

    private final TikzGraph graph;
    private final JTextArea textArea;
    private final SourceController controller;
    private Boolean isFocused = false;
    private final EditorView parentView;

    /**
     * Constructs a new View for the Source,
     * with a given TikzGraph and parentView
     * @param parentView The view which contains this view (ie. EditorView)
     * @param graph The TikzGraph
     */
    public SourceView(EditorView parentView, TikzGraph graph) {
        this.parentView = parentView;
        this.graph = graph;

        this.controller = new SourceController(this, graph);
        this.textArea = new JTextArea();
        this.setPreferredSize(new Dimension(TEXT_AREA_WIDTH, this.getHeight()));

        this.addListeners();
        this.render();
    }

    /**
     * Renders the view by setting its layout and adding a scroll pane
     */
    private void render() {
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    /**
     * Adds listeners for focus and document events to the view
     * Reacts to a loss/gain of focus
     * Reacts to a modification to the text area
     */
    private void addListeners() {
        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                controller.focusGained();
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                controller.focusLost();
            }
        });

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                controller.textIsUpdated();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                controller.textIsUpdated();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                controller.textIsUpdated();
            }
        });
    }

    /**
     * Returns true if this view is focused
     * @return true if this view is focused
     */
    public Boolean getIsFocused() {
        return isFocused;
    }

    /**
     * Sets whether the view is focused or not
     * @param isFocused The boolean to set whether the view is focused or not
     */
    public void setIsFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }

    /**
     * Getter for the text area which contains the tikz text
     * @return the tikz text contained in the text area
     */
    public String getText() {
        return textArea.getText();
    }

    /**
     * Setter for the text area which contains the tikz text
     * @param text The tikz text which will be contained in the text area
     */
    public void setText(String text) {
        textArea.setText(text);
    }

    /**
     * Getter for the current project path
     * @return The path of the current project
     */
    public String getProjectPath() {
        return parentView.getProjectPath();
    }
}
