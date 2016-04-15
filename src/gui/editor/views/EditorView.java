package gui.editor.views;

import gui.editor.controllers.*;
import models.TikzGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Paths;

public class EditorView extends JFrame{
    private TikzGraph graph;

    private CanvasView canvasView;
    private SourceView sourceView;
    private MenuView menuView;
    private String projectPath;

    private EditorController controller;

    public EditorView(String filePath, Boolean isImport){
        this.projectPath = filePath;
        if(isImport){
            this.graph = new TikzGraph(filePath);
            this.projectPath = Paths.get(getProjectPath()).getParent().toString();
        }else{
            this.graph = new TikzGraph();
        }

        this.canvasView = new CanvasView(this,graph);
        this.sourceView = new SourceView(this, graph);
        this.menuView = new MenuView(this, graph);

        this.controller = new EditorController(this, graph);

        this.graph.replace(this.graph);//update du tikZ text

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                menuView.save();
                super.windowClosing(windowEvent);
            }
        });

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

    public String getProjectPath() {
        return projectPath;
    }
}
