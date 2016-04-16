package gui.editor.controllers;

import models.TikzGraph;
import gui.editor.views.EditorView;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the Editor.
 * The Editor is the main element of the GUI which contains the other elements of the GUI.
 */
public class EditorController {
    private final EditorView view;
    private final TikzGraph graph;

    /**
     * Constructs a new Controller (from the MVC architecural pattern) for the Editor,
     * with a given TikzGraph and EditorView
     * @param view The EditorView which is associated with this controller
     * @param graph The TikzGraph
     */
    public EditorController(EditorView view, TikzGraph graph) {
        this.view = view;
        this.graph = graph;
    }
}
