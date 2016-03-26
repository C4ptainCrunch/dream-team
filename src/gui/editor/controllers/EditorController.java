package gui.editor.controllers;

import gui.editor.views.EditorView;
import models.TikzGraph;

public class EditorController {
    private EditorView view;
    private TikzGraph graph;

    public EditorController(EditorView view, TikzGraph graph) {
        this.view = view;
        this.graph = graph;
    }
}
