package gui.editor.controllers;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.*;

import models.TikzGraph;

import org.codehaus.jparsec.error.ParserException;

import parser.NodeParser;
import utils.SaverFactory;
import gui.editor.views.SourceView;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the Source.
 * The Source is the area of the GUI where the Tikz is edited.
 */
public class SourceController implements Observer {
    private static final Logger logger = Logger.getLogger("gui.editor.controllers.source");
    private final SourceView view;
    private final TikzGraph graph;
    private String originalText = "";

    /**
     *Constructs a new Controller (from the MVC architecural pattern) for the Source,
     * with a given TikzGraph and SourceView
     *
     * @param view  The SourceView which is associated with this controller
     * @param graph The TikzGraph
     */
    public SourceController(SourceView view, TikzGraph graph) {
        this.view = view;
        this.graph = graph;
        this.graph.addObserver(this);
    }

    /**
     * Called when Observables linked to this Observer call notify(),
     * updates the Tikz text and saves the modifications
     *
     * @param o The Observable
     * @param arg The arguments given by the Observable
     */
    public void update(Observable o, Object arg) {
        System.out.println("update");
        if (!view.getIsFocused()) {
            String tmp = originalText;
            String newText = this.graph.toString();
            view.setText(newText);
            Thread t = new Thread(() -> {
                System.out.println(originalText);
                new SaverFactory().writeToFile(tmp, newText, view.getProjectPath());
            });
            t.start();
            originalText = newText;
        }
    }

    /**
     * Updates the graph if modifications occured in the Tikz text
     *
     * @param raw_text The new Tikz text
     */
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

    /**
     * Sets the focus of the view at true meaning that the user has clicked in the text area
     */
    public void focusGained() {
        view.setIsFocused(true);
    }

    /**
     * Sets the focus of the view at false meaning that the user has clicked out of the text area
     * and updates from text
     */
    public void focusLost() {
        view.setIsFocused(false);
        this.updateFromText(view.getText());
    }

    /**
     * Updates from text if the view is focused
     */
    public void textIsUpdated() {
        if (view.getIsFocused()) {
            this.updateFromText(view.getText());
        }
    }
}
