package gui.editor.controllers;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import models.tikz.TikzComponent;
import models.tikz.TikzEdge;
import models.tikz.TikzGraph;
import models.tikz.TikzNode;
import gui.editor.views.CanvasView;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the Canvas.
 * The Canvas is the area of the GUI where the graph is painted.
 */
public class CanvasController implements Observer {
    private final CanvasView view;
    private final TikzGraph graph;
    private final Set<DrawableTikzComponent> draws;
    private final CanvasState state;

    /**
     * Constructs a new Controller for the Canvas,
     * with a given TikzGraph and CanvasView
     * @param view The CanvasView which is associated with this controller
     * @param graph The TikzGraph
     */
    public CanvasController(CanvasView view, TikzGraph graph) {
        this.view = view;

        this.graph = graph;
        this.graph.addObserver(this);
        draws = new HashSet<>();
        state = new CanvasState();
    }

    /**
     * Called when Observables linked to this Observer call notify(),
     * repaints the graph
     *
     * @param o The Observable
     * @param arg The arguments given by the Observable
     */
    public void update(Observable o, Object arg) {
        view.repaint();
    }

    /**
     * Finds a component of the graph by its position
     *
     * @param position The position
     * @return a tikz component
     */
    private TikzComponent findComponentByPosition(Point position) {
        TikzComponent comp = null;
        for (DrawableTikzComponent draw : draws) {
            if (draw.contains(position)) {
                comp = draw.getComponent();
                break;
            }
        }
        return comp;
    }

    /**
     * Adds a node to the graph
     *
     * @param component The component to be added
     * @param position The position where the component will be added
     */
    private void addNodeToModel(TikzComponent component, Point position) {
        TikzNode node = (TikzNode) component;
        node.setPosition(position);
        graph.add(node.getClone());
    }

    /**
     * Adds an edge between two nodes of the graph
     *
     * @param edge The edge to be added
     * @param source The source node from which the edge will start
     * @param destination The destination node to which the edge will end
     */
    private void addEdgeToGraph(TikzEdge edge, TikzNode source, TikzNode destination) {
        if ((source != null) && (destination != null)) {
            edge.setFirstNode(source);
            edge.setSecondNode(destination);
            graph.add(edge);
        }
    }

    /**
     * Adds an edge to the Canvas that will end at a given node given by its position.
     * Succeeds only if the position clicked is where a node exists
     *
     * @param component The edge to be added
     * @param position The position of the component to which the edge will end
     */
    private void addEdgeToModel(TikzComponent component, Point position) {
        TikzComponent clickedComponent = findComponentByPosition(position);
        if (clickedComponent != null) {
            if (state.initialized()) {
                TikzEdge edge = (TikzEdge) component;
                addEdgeToGraph(edge, (TikzNode) state.getRelatedComponent(), (TikzNode) clickedComponent);
                state.reset();
            } else {
                state.setComponent(component);
                state.setRelatedComponent(clickedComponent);
            }
        }

    }

    /**
     * Signals that the mouse is being clicked on
     * @param e The MouseEvent
     * @param selectedTool The component that has been clicked on
     */
    public void mousePressed(MouseEvent e, TikzComponent selectedTool) {
        if (view.getIsFocused()) {
            if (selectedTool instanceof TikzNode) { // TODO : Need to refactor
                                                    // this.
                addNodeToModel(selectedTool, e.getPoint());
            } else if (selectedTool instanceof TikzEdge) {
                addEdgeToModel(selectedTool, e.getPoint());
            }
        } else {
            view.requestFocusInWindow();
        }
    }

    /**
     * Signals that the mouse click has been dropped
     *
     * @param component The component associated with the click
     * @param location The position of the pointer where the mouse click has been dropped
     */
    public void mouseDropped(TikzComponent component, Point location) {
        if (component instanceof TikzNode) { // TODO : And this.
            addNodeToModel(component, location);
        }
    }

    /**
     * Sets the focus of the view at true meaning that the user has clicked in the canvas area,
     * repaints the graph
     */
    public void focusGained() {
        view.setIsFocused(true);
        view.repaint();
    }

    /**
     * Sets the focus of the view at false meaning that the user has clicked out of the canvas area,
     * repaints the graph
     */
    public void focusLost() {
        view.setIsFocused(false);
        view.repaint();
    }

    /**
     * Adds a drawable tikz component to the draws set
     * @param draw The DrawableTikzComponent to be added
     */
    public void addDrawableComponent(DrawableTikzComponent draw) {
        draws.add(draw);
    }
}


/**
 * This Class allows the CanvasController to keep a state of one action (here, a
 * mouse clicked on a component).
 */
class CanvasState {

    private TikzComponent component;
    private TikzComponent related_component;

    /**
     *Constructs a new Canvas state with an empty component and related component
     */
    public CanvasState() {
        component = null;
        related_component = null;
    }

    /**
     * Constructs a new Canvas state with two given tikz components
     * @param comp The component
     * @param pos The related component
     */
    public CanvasState(TikzComponent comp, TikzComponent pos) {
        component = comp;
        related_component = pos;
    }

    /**
     *Compare whether a given canvas state is equals to this canvas state
     * @param o_state The other canvas state to be compared with
     * @return whether the given canvas state is equals to this canavs state
     */
    public boolean equalsTo(CanvasState o_state) {
        return ((o_state.getComponent() == this.component)
                && (o_state.getRelatedComponent() == this.getRelatedComponent()));
    }

    /**
     * Returns true if the component and the related component are both initialized
     * @return true if the component and the related component are both not null
     */
    public boolean initialized() {
        return ((component != null) && (related_component != null));
    }

    /**
     * Returns true if the component is equals to the specified component
     * @param comp the component to be compared with
     * @return true if the component and the specified component are equals
     */
    public boolean componentEqualsTo(TikzComponent comp) {
        return comp == this.component;
    }

    /**
     * Getters for the component
     * @return the component
     */
    public TikzComponent getComponent() {
        return component;
    }

    /**
     * Setter for the componenet
     * @param component the component to be set with
     */
    public void setComponent(TikzComponent component) {
        this.component = component;
    }

    /**
     * Getter for the relative component
     * @return The relative component
     */
    public TikzComponent getRelatedComponent() {
        return related_component;
    }

    /**
     * Setter for the relative component
     * @param related_component the relative component to be set with
     */
    public void setRelatedComponent(TikzComponent related_component) {
        this.related_component = related_component;
    }

    /**
     * Sets the component and the relative component to null
     */
    public void reset() {
        this.component = null;
        this.related_component = null;
    }
}
