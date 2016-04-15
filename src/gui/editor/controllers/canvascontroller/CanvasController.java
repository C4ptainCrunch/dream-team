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
        TikzComponent comp = null;
        for (DrawableTikzComponent draw : draws){
            if (draw.contains(position)){
                comp = draw.getComponent();
                break;
            }
        }
        return comp;
    }

    private void addNodeToModel(TikzComponent component, Point position){
        TikzNode node = (TikzNode) component;
        node.setPosition(position);
        graph.add(node.getClone());
    }

    private void addEdgeToGraph(TikzEdge edge, TikzNode source, TikzNode destination){
        if ((source != null) && (destination != null)) {
            edge.setFirstNode(source);
            edge.setSecondNode(destination);
            graph.add(edge);
        }
    }

    private void addEdgeToModel(TikzComponent component, Point position){
        TikzComponent clickedComponent = findComponentByPosition(position);
        if(clickedComponent != null){
            if (!state.initialized()){
                state.setComponent(component);
                state.setRelatedComponent(clickedComponent);
            }
            else {
                TikzEdge edge = (TikzEdge) component;
                addEdgeToGraph(edge,(TikzNode) state.getRelatedComponent(), (TikzNode) clickedComponent);
                state.reset();
            }
        }

    }

    public void mousePressed(MouseEvent e, TikzComponent selectedTool) {
        if(view.getIsFocused()){
            if (selectedTool instanceof TikzNode){ // TODO : Need to refactor this.
                addNodeToModel(selectedTool, e.getPoint());
            }
            else if (selectedTool instanceof TikzEdge) {
                addEdgeToModel(selectedTool, e.getPoint());
            }
        }
        else {
            view.requestFocusInWindow();
        }
    }

    public void mouseDropped(TikzComponent component, Point location){
        if (component instanceof TikzNode) {    // TODO : And this.
            addNodeToModel(component, location);
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

    public void addDrawableComponent(DrawableTikzComponent draw){
        draws.add(draw);
    }
}
