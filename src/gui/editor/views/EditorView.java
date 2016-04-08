package gui.editor.views;

import gui.editor.controllers.*;
import models.TikzGraph;
import models.TikzNode;
import models.TikzRectangle;
import parser.NodeParser;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EditorView extends JFrame{
    private TikzGraph graph;

    private CanvasView canvasView;
    private SourceView sourceView;
    private MenuView menuView;
    private String path;

    private EditorController controller;

    public EditorView(String filePath, Boolean isImport){
        this.path = filePath;
        if(isImport){
            this.graph = new TikzGraph(filePath);
            this.path = Paths.get(path).getParent().toString();
        }else{
            this.graph = new TikzGraph();
        }

        this.canvasView = new CanvasView(this,graph);
        this.sourceView = new SourceView(this, graph, path);
        this.menuView = new MenuView(this, graph, path);

        this.controller = new EditorController(this, graph);

        this.graph.replace(this.graph);//update du tikZ text

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.render();
        this.setVisible(true);
    }

    public void render(){
        this.setTitle("CreaTikZ");

        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setLocationRelativeTo(null);

        Container pane = getContentPane();
        pane.setLayout(new GridLayout(1,2));

        pane.add(this.canvasView);
        pane.add(this.sourceView);

        this.setJMenuBar(menuView);
    }
}
