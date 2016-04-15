package gui.editor.controllers;

import models.TikzGraph;
import gui.editor.views.EditorView;

public class EditorController {
    private EditorView view;
    private TikzGraph graph;

    public EditorController(EditorView view, TikzGraph graph) {
        this.view = view;
        this.graph = graph;
    }
}
