package gui.editor.views;

import gui.editor.controllers.SourceController;
import models.TikzGraph;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class SourceView extends JPanel implements Observer{
    private TikzGraph graph;
    private JTextArea textArea;
    private SourceController controller;
    private Boolean textUpdateIsManual = true;

    public SourceView(TikzGraph graph){
        this.graph = graph;
        this.graph.addObserver(this);

        this.controller = new SourceController(this, graph);

        this.textArea = new JTextArea();


        this.addListeners();
        this.render();
    }

    public void update(Observable o, Object arg){
        textUpdateIsManual = false;
        textArea.setText(this.graph.toString());
        textUpdateIsManual = true;
    }

    private void render(){
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    private void addListeners(){
        textArea.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (textUpdateIsManual){
                    controller.updateFromText(textArea.getText());
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                if (textUpdateIsManual){
                    controller.updateFromText(textArea.getText());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                if (textUpdateIsManual){
                    controller.updateFromText(textArea.getText());
                }
            }
        });
    }
}
