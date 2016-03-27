package gui.editor.views;

import gui.editor.controllers.*;
import models.TikzGraph;
import models.TikzNode;
import models.TikzRectangle;

import javax.swing.*;
import java.awt.*;

public class EditorView extends JFrame{
    private TikzGraph graph;

    private CanvasView canvasView;
    private SourceView sourceView;
    private MenuView menuView;

    private EditorController controller;

    public EditorView(){
        this(new TikzGraph());

        TikzNode nodeR = new TikzRectangle(100, 100);
        nodeR.setLabel("Demo Label");
        nodeR.setPosition(new Point(250, 200));
        graph.add(nodeR);
    }

    public EditorView(TikzGraph graph){
        this.graph = graph;

        this.canvasView = new CanvasView(graph);
        this.sourceView = new SourceView(graph);
        this.menuView = new MenuView(graph);

        this.controller = new EditorController(this, graph);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.render();
        this.setVisible(true);
    }

    public void render(){
        this.setTitle("CreaTikZ");

        Container pane = getContentPane();
        pane.setLayout(new GridLayout(1,2));

        pane.add(this.canvasView);
        pane.add(this.sourceView);

        this.setJMenuBar(menuView);
    }
}
