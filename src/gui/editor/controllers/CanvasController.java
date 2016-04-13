package gui.editor.controllers;

import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import gui.editor.views.canvas.drawers.ComponentDrawer;
import models.TikzCircle;
import models.TikzComponent;
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

    public void mousePressed(MouseEvent e, ComponentDrawer drawer) {
        if(view.getIsFocused()){
            TikzComponent comp = drawer.getComponent();
            TikzNode node = (TikzNode) comp;
            node.setPosition(e.getPoint());
            graph.add(node.getClone());
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
