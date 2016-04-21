package controllers.editor;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.*;

import models.tikz.TikzGraph;

import org.codehaus.jparsec.error.ParserException;

import parser.NodeParser;
import utils.Log;
import views.editor.SourceView;

/**
 * Implementation of the Controller for the Source. The Source is the area of
 * the GUI where the Tikz is edited.
 */
public class SourceController implements Observer {
    private static final Logger logger = Log.getLogger(SourceController.class);
    private final SourceView view;
    private final TikzGraph graph;

    /**
     * Constructs a new Controller (from the MVC architecural pattern) for the
     * Source, with a given TikzGraph and SourceView
     *
     * @param view
     *            The SourceView which is associated with this controller
     * @param graph
     *            The TikzGraph
     */
    public SourceController(SourceView view, TikzGraph graph) {
        this.view = view;
        this.graph = graph;
        this.graph.addObserver(this);
        //updateTikzText();
    }

    /**
     * Called when Observables linked to this Observer call notify(), updates
     * the Tikz text and saves the modifications
     *
     * @param o
     *            The Observable
     * @param arg
     *            The arguments given by the Observable
     */
    public void update(Observable o, Object arg) {
        logger.finest("Got an update event");
        if (!view.getIsFocused()) {
            updateTikzText();
        }
    }

    /**
     * Updates the graph if modifications occured in the Tikz text
     *
     * @param raw_text
     *            The new Tikz text
     */
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

    /**
     * Updates the tikz text from the graph
     */
    public void updateTikzText() {
        String tikz = this.graph.toString();
        view.setText(tikz);
    }

    /**
     * Sets the focus of the view at true meaning that the user has clicked in
     * the text area
     */
    public void focusGained() {
        logger.finest("Focus gained");
        view.setIsFocused(true);
    }

    /**
     * Sets the focus of the view at false meaning that the user has clicked out
     * of the text area and updates from text
     */
    public void focusLost() {
        logger.finest("Focus lost");
        view.setIsFocused(false);
        this.updateGraphFromText(view.getText());
    }

    /**
     * Updates the graph from text if the view is focused
     */
    public void updateGraphIfFocused() {
        if (view.getIsFocused()) {
            this.updateGraphFromText(view.getText());
        }
    }
}
