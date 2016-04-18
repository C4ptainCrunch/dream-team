package gui.editor.views;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import models.tikz.TikzGraph;
import gui.editor.controllers.SourceController;

public class SourceView extends JPanel {
    private static final int TEXT_AREA_WIDTH = 300;

    private final TikzGraph graph;
    private final JTextArea textArea;
    private final SourceController controller;
    private Boolean isFocused = false;
    private final EditorView parentView;

    public SourceView(EditorView parentView, TikzGraph graph) {
        this.parentView = parentView;
        this.graph = graph;

        this.controller = new SourceController(this, graph);
        this.textArea = new JTextArea();
        this.setPreferredSize(new Dimension(TEXT_AREA_WIDTH, this.getHeight()));

        this.addListeners();
        this.render();
    }

    private void render() {
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

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
                controller.textWasUpdated();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                controller.textWasUpdated();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                controller.textWasUpdated();
            }
        });
    }

    public Boolean getIsFocused() {
        return isFocused;
    }

    public void setIsFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
    }

}
