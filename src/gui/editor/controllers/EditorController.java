package gui.editor.controllers;

import models.TikzGraph;
import gui.editor.views.EditorView;

public class EditorController {
    private final EditorView view;
    private final TikzGraph graph;

    public EditorController(EditorView view, TikzGraph graph) {
        this.view = view;
        this.graph = graph;
    }
}
