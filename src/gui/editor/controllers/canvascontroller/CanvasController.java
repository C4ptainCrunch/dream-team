package gui.editor.controllers.canvascontroller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import gui.editor.views.canvas.drawables.DrawableTikzComponent;
import models.*;
import gui.editor.views.CanvasView;

public class CanvasController implements Observer{
    private CanvasView view;
    private TikzGraph graph;
    private HashSet<DrawableTikzComponent> draws;
    private CanvasControllerState state;

    public CanvasController(CanvasView view, TikzGraph graph) {
        this.view = view;

        this.graph = graph;
        this.graph.addObserver(this);
        draws = new HashSet<>();
        state = new CanvasControllerState();
    }

    public void update(Observable o, Object arg){
        view.repaint();
    }

    private TikzComponent findComponentByPosition(Point position){
        TikzComponent comp = new TikzVoid();
        for (DrawableTikzComponent draw : draws){
            if (draw.contains(position)){
                comp = draw.getComponent();
            }
        }
        return comp;
    }

    private void addNodeToModel(TikzComponent component, Point position){
        TikzNode node = (TikzNode) component;
        node.setPosition(position);
        graph.add(node.getClone());
    }

    private void addEdgeToGraph(TikzEdge edge, TikzNode f_node, TikzNode s_node){
        if ((f_node != null) && (s_node != null)) {
            edge.setFirstNode(f_node);
            edge.setSecondNode(s_node);
            graph.add(edge);
        }
    }

    private void addEdgeToModel(TikzComponent component, Point position){
        if (!state.initialized()){
            state.setComponent(component);
            state.setRelatedComponent(findComponentByPosition(position));
        }
        else {
            TikzComponent comp = findComponentByPosition(position);
            TikzEdge edge = (TikzEdge) component;
            addEdgeToGraph(edge,(TikzNode) state.getRelatedComponent(), (TikzNode) comp);
            state.reset();
        }

    }

    public void mousePressed(MouseEvent e, TikzComponent comp) {
        if(view.getIsFocused()){
            if (comp instanceof TikzNode){      // Need to refactor this.
                addNodeToModel(comp, e.getPoint());
            }
            else if (comp instanceof TikzEdge) {
                addEdgeToModel(comp, e.getPoint());
            }
        }
        else {
            view.requestFocusInWindow();
        }
    }

    public void mouseDropped(TikzComponent component, Point location){
        addNodeToModel(component, location);

    }

    public void focusGained() {
        view.setIsFocused(true);
        view.repaint();
    }

    public void focusLost() {
        view.setIsFocused(false);
        view.repaint();
    }

    public void addDrawableComponent(DrawableTikzComponent draw){
        draws.add(draw);
    }
}
