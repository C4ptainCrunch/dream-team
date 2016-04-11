package gui.editor.views;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;
import static constants.GUI.Text.*;
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
        this.setTitle(APP_NAME);

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setLocationRelativeTo(null);

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        pane.add(this.toolView, BorderLayout.WEST);
        pane.add(this.canvasView, BorderLayout.CENTER);
        pane.add(this.sourceView, BorderLayout.EAST);

        this.setJMenuBar(menuView);
    }

    public HashMap<String, Object> getCurrentToolProperties(){
        return this.toolView.getProperties();
    }
}
