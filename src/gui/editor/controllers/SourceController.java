package gui.editor.controllers;


import gui.editor.views.SourceView;
import models.TikzGraph;
import org.codehaus.jparsec.error.ParserException;
import parser.NodeParser;

import javax.swing.*;
import java.util.logging.Logger;

public class SourceController {
    private SourceView view;
    private TikzGraph graph;
    private static final Logger logger = Logger.getLogger("gui.editor.controllers.source");

    public SourceController(SourceView view, TikzGraph graph) {
        this.view = view;
        this.graph = graph;
    }

    public void updateFromText(String text) {
        text = text.trim();
        if(text.length() != 0) {
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
}
