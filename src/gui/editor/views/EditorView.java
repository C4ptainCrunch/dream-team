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
    private ToolboxView toolboxView;

    private EditorController controller;

    public EditorView(){
        this(new TikzGraph());
    }

    public EditorView(TikzGraph graph){
        this.graph = graph;

        this.canvasView = new CanvasView(this,graph);
        this.sourceView = new SourceView(this, graph);
        this.menuView = new MenuView(this, graph);
        this.toolboxView = new ToolboxView(this);

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

        pane.add(this.toolboxView);
        pane.add(this.canvasView);
        pane.add(this.sourceView);

        this.setJMenuBar(menuView);
    }
}
