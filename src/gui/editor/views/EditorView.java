package gui.editor.views;

import java.awt.*;

import javax.swing.*;

import models.TikzGraph;
import gui.editor.controllers.EditorController;
import gui.editor.toolbox.views.ToolView;

public class EditorView extends JFrame{
    private TikzGraph graph;

    private CanvasView canvasView;
    private SourceView sourceView;
    private MenuView menuView;
    private ToolView toolView;

    private EditorController controller;

    public EditorView(){
        this(new TikzGraph());
    }

    public EditorView(TikzGraph graph){
        this.graph = graph;

        this.canvasView = new CanvasView(this,graph);
        this.sourceView = new SourceView(this, graph);
        this.menuView = new MenuView(this, graph);
        this.toolView = new ToolView();

        this.controller = new EditorController(this, graph);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.render();
        this.setVisible(true);
    }

    public void render(){
        this.setTitle("CreaTikZ");

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setLocationRelativeTo(null);

        Container pane = getContentPane();
        pane.setLayout(new GridLayout(1,3));

        pane.add(this.toolView);
        pane.add(this.canvasView);
        pane.add(this.sourceView);

        this.setJMenuBar(menuView);
    }
}
