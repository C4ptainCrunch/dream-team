package controllers.editor;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.List;

import constants.Errors;
import misc.CanvasSelection;
import misc.drag.transfertdata.TransferTikz;
import models.Template;
import views.editor.canvas.NodeEditionView;
import models.tikz.TikzComponent;
import models.tikz.TikzEdge;
import models.tikz.TikzGraph;
import models.tikz.TikzNode;
import views.editor.CanvasView;
import views.editor.canvas.drawables.DrawableTikzComponent;
import views.editor.canvas.drawers.Drawer;
import views.management.FileChooseView;

import javax.swing.*;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the Canvas.
 * The Canvas is the area of the GUI where the graph is painted.
 */
public class CanvasController implements Observer {
    private final CanvasView view;
    private final TikzGraph graph;
    private final Set<DrawableTikzComponent> draws;
    private final Set<TikzComponent> selected_comp;
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
        selected_comp = new HashSet<>();
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
    public TikzComponent findComponentByPosition(Point position) {
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
     * Return true if there is a component of the graph at the given position
     *
     * @param position The position
     * @return A boolean value
     */

    public boolean hasComponentAtPosition(Point position) {
        if (findComponentByPosition(position) != null){
            return true;
        }
        return false;
    }

    public void removeFromDraws(TikzComponent comp){
        for (DrawableTikzComponent draw : draws){
            if (draw.hasAsComponent(comp)){
                draws.remove(draw);
                break;
            }
        }
    }

    public Set<TikzComponent> getSelectedComponents(List<Point> positions){
        for (Point pos: positions){
            TikzComponent comp = findComponentByPosition(pos);
            if (comp != null){
                selected_comp.add(comp);
                removeFromDraws(comp);
            }
        }
        return selected_comp;
    }

    public void unselectComponents(){
        for (TikzComponent comp: selected_comp){
            addDrawableComponent(Drawer.toDrawable(comp));
        }
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
        graph.add(node);
        view.resetTool();
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
                view.resetTool();
            } else {
                state.setComponent(component);
                state.setRelatedComponent(clickedComponent);
            }
        }

    }

    private void addGraph(TikzGraph g, Point location){
        g.translation((int) location.getX(), (int) location.getY());
        graph.add(g);
    }

    private void moveComponent(TikzComponent comp, Point location){
        if (comp instanceof TikzNode){      // TODO: Refactor this
            ((TikzNode)comp).setPosition(location);
            view.repaint();
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
     * @param transfer_data The component associated with the click
     * @param location The position of the pointer where the mouse click has been dropped
     */

    public void mouseDropped(TransferTikz transfer_data, Point location) {
        switch (transfer_data.getOption()){
            case MOVE:
                moveComponent(transfer_data.getComponent(), location);
                break;
            case ADD:
                addGraph(transfer_data.getGraph(), location);
                break;
            default:
                break;

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

    public void deleteItem(TikzComponent itemToDelete) {
        if (itemToDelete != null){
            if (itemToDelete instanceof TikzNode) {
                this.graph.remove((TikzNode) itemToDelete);
            }else {
                this.graph.remove((TikzEdge) itemToDelete);
            }
        }
    }

    public void editItem(TikzComponent itemToEdit){
        new NodeEditionView(itemToEdit);
    }

    // TODO: Move next method into dedicated class.

    public void exportSelectionAsTemplate(){
        CanvasSelection selection = view.getSelection();
        FileChooseView file_view = new FileChooseView("Template filename", JFileChooser.FILES_AND_DIRECTORIES);
        if (selection != null){
            Set<TikzComponent> components = getSelectedComponents(selection.getShapePoints());
            TikzGraph g = new TikzGraph();
            for (TikzComponent comp: components){
                if (comp instanceof TikzNode){      //TODO: Refactor this;
                    g.add((TikzNode) comp);
                }
            }
            Template template = new Template(g);
            try {
                template.saveTemplate(file_view.ask());
                unselectComponents();
            } catch (IOException e){
                JOptionPane.showMessageDialog(view, Errors.SAVE_TEMPLATE_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
            }
        }
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
