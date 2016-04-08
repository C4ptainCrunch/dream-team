package gui.editor.views;

import gui.editor.controllers.SourceController;
import models.TikzGraph;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Observable;
import java.util.Observer;

public class SourceView extends JPanel{
    private TikzGraph graph;
    private JTextArea textArea;
    private SourceController controller;
    private Boolean isFocused = false;
    private EditorView parentView;
    private String path;

    public SourceView(EditorView parentView, TikzGraph graph, String path){
        this.parentView = parentView;
        this.graph = graph;
        this.path = path;

        this.controller = new SourceController(this, graph);
        this.textArea = new JTextArea();

        this.addListeners();
        this.render();
    }

    public void setText(String text){
        textArea.setText(text);
    }

    private void render(){
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    private void addListeners(){
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

    public Boolean getIsFocused() {
        return isFocused;
    }

    public void setIsFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }

    public String getText() {
        return textArea.getText();
    }


    public String getPath() {
        return path;
    }
}
