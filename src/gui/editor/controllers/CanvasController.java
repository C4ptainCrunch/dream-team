package gui.editor.controllers;

import gui.editor.views.CanvasView;
import models.TikzGraph;

public class CanvasController {
    private CanvasView view;
    private TikzGraph graph;

    public CanvasController(CanvasView view, TikzGraph graph) {
        this.view = view;
        this.graph = graph;
    }
}
