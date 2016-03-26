package gui.editor.controllers;


import gui.editor.views.SourceView;
import models.TikzGraph;
import org.codehaus.jparsec.error.ParserException;
import parser.NodeParser;

import javax.swing.*;

public class SourceController {
    private SourceView view;
    private TikzGraph graph;

    public SourceController(SourceView view, TikzGraph graph) {
        this.view = view;
        this.graph = graph;
    }

    public void updateFromText(String text) {
        if(text.trim().length() != 0) {
            TikzGraph new_graph = new TikzGraph();
            try {
                NodeParser.parseTikzDocument(new_graph).parse(text);
                SwingUtilities.invokeLater(() -> {
                    graph.replace(new_graph);
                    System.out.println("Runnable done");
                });

                System.out.println("Valid graph, " + graph.getNodes().size() + " nodes");
            } catch (ParserException e) {
                System.out.println(e);
            }
        }
    }
}
