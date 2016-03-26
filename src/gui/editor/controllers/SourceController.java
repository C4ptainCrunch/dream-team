package gui.editor.controllers;


import gui.editor.views.SourceView;
import models.TikzGraph;

public class SourceController {
    private SourceView view;
    private TikzGraph graph;

    public SourceController(SourceView view, TikzGraph graph) {
        this.view = view;
        this.graph = graph;
    }

    public void updateFromText(String text) {
        System.out.println("====================\n" + text + "\n------------------");
    }
}
