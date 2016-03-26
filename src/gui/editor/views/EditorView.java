package gui.editor.views;

import models.TikzGraph;
import models.TikzNode;
import models.TikzRectangle;

import javax.swing.*;
import java.awt.*;

public class EditorView extends JFrame{
    private TikzGraph graph;
    private CanvasView canvasView;

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

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.render();
        this.setVisible(true);
    }

    public void render(){
        this.setTitle("CreaTikZ");
        this.setContentPane(this.canvasView);
    }
}
