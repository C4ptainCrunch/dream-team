package gui.editor.controllers;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.*;

import models.tikz.TikzGraph;

import org.codehaus.jparsec.error.ParserException;

import parser.NodeParser;
import utils.SaverUtil;
import gui.editor.views.SourceView;

public class SourceController implements Observer {
    private static final Logger logger = Logger.getLogger("gui.editor.controllers.source");
    private final SourceView view;
    private final TikzGraph graph;

    public SourceController(SourceView view, TikzGraph graph) {
        this.view = view;
        this.graph = graph;
        this.graph.addObserver(this);
    }

    public void update(Observable o, Object arg) {
        if (!view.getIsFocused()) {
            String tikz = this.graph.toString();
            view.setText(tikz);
        }
    }

    private void updateFromText(String raw_text) {
        String text = raw_text.trim();
        if (text.length() != 0) {
            TikzGraph new_graph = new TikzGraph();
            logger.fine("TikZ updated event. New TikZ : " + text);
            try {
                NodeParser.parseDocument(new_graph).parse(text);
                logger.fine("Valid graph from TikzSource : " + graph.getNodes().size() + " nodes");
                SwingUtilities.invokeLater(() -> {
                    graph.replace(new_graph);
                    logger.fine("Runnable done: graph replace");
                });

            } catch (ParserException e) {
                logger.warning("Error during TikZ parsing : " + e.getMessage());
            }
        }
    }

    public void focusGained() {
        view.setIsFocused(true);
    }

    public void focusLost() {
        view.setIsFocused(false);
        this.updateFromText(view.getText());
    }

    public void textIsUpdated() {
        if (view.getIsFocused()) {
            this.updateFromText(view.getText());
        }
    }
}
