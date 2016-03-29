package gui.editor.controllers;

import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import models.TikzCircle;
import models.TikzGraph;
import models.TikzNode;
import gui.editor.views.CanvasView;

public class CanvasController implements Observer{
    private CanvasView view;
    private TikzGraph graph;

    public CanvasController(CanvasView view, TikzGraph graph) {
        this.view = view;

        this.graph = graph;
        this.graph.addObserver(this);
    }

    public void update(Observable o, Object arg){
        view.repaint();
    }

    public void mousePressed(MouseEvent e) {
        if(view.getIsFocused()){
            TikzNode nodeR2 = new TikzCircle();
            nodeR2.setLabel("Demo Label " + graph.getAll().size());
            nodeR2.setPosition(e.getPoint());
            graph.add(nodeR2);
        }
        else {
            view.requestFocusInWindow();
        }
    }

    public void focusGained() {
        view.setIsFocused(true);
        view.repaint();
    }

    public void focusLost() {
        view.setIsFocused(false);
        view.repaint();
    }
}
