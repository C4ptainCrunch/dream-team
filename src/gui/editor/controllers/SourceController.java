package gui.editor.controllers;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.*;

import models.tikz.TikzGraph;

import org.codehaus.jparsec.error.ParserException;

import parser.NodeParser;
import utils.Log;
import gui.editor.views.SourceView;

public class SourceController implements Observer {
    private static final Logger logger = Log.getLogger(SourceController.class);
    private final SourceView view;
    private final TikzGraph graph;

    public SourceController(SourceView view, TikzGraph graph) {
        this.view = view;
        this.graph = graph;
        this.graph.addObserver(this);
    }

    public void update(Observable o, Object arg) {
        logger.finest("Got an update event");
        if (!view.getIsFocused()) {
            String tikz = this.graph.toString();
            view.setText(tikz);
        }
    }

    private void updateGraphFromText(String raw_text) {
        String text = raw_text.trim();
        if (text.length() != 0) {
            TikzGraph new_graph = new TikzGraph();
            try {
                NodeParser.parseDocument(new_graph).parse(text);
                logger.fine("Valid graph from TikzSource");
                SwingUtilities.invokeLater(() -> {
                    graph.replace(new_graph);
                });

            } catch (ParserException e) {
                logger.info("Error during TikZ parsing : " + e.getMessage());
            }
        }
    }

    public void focusGained() {
        logger.finest("Focus gained");
        view.setIsFocused(true);
    }

    public void focusLost() {
        logger.finest("Focus lost");
        view.setIsFocused(false);
        this.updateGraphFromText(view.getText());
    }

    public void textWasUpdated() {
        if (view.getIsFocused()) {
            this.updateGraphFromText(view.getText());
        }
    }
}
