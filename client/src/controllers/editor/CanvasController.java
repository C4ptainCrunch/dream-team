package controllers.editor;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.*;

import misc.CanvasSelection;
import misc.drag.transfereddata.TransferTikz;
import misc.managers.TemplateIOManager;
import misc.utils.Converter;
import models.tikz.TikzComponent;
import models.tikz.TikzEdge;
import models.tikz.TikzGraph;
import models.tikz.TikzNode;
import views.editor.CanvasView;
import views.editor.canvas.NodeEditionView;
import views.editor.canvas.drawables.DrawableTikzComponent;
import views.editor.canvas.drawers.Drawer;
import constants.Errors;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the
 * Canvas. The Canvas is the area of the GUI where the graph is painted.
 */
public class CanvasController implements Observer {
    private final CanvasView view;
    private final TikzGraph graph;

    private final java.util.List<DrawableTikzComponent> drawables;
    private final Set<TikzComponent> selected_comp;
    private final CanvasState state;

    /**
     * Constructs a new Controller for the Canvas, with a given TikzGraph and
     * CanvasView
     *
     * @param view
     *            The CanvasView which is associated with this controller
     * @param graph
     *            The TikzGraph
     */
    public CanvasController(final CanvasView view, final TikzGraph graph) {
        this.view = view;

        this.graph = graph;
        this.graph.addObserver(this);
        drawables = new java.util.ArrayList<>();
        state = new CanvasState();
        selected_comp = new HashSet<>();
    }

    /**
     * Called when Observables linked to this Observer call notify(), repaints
     * the graph
     *
     * @param o
     *            The Observable
     * @param arg
     *            The arguments given by the Observable
     */
    public void update(final Observable o, final Object arg) {
        view.repaint();
    }

    public void updateDrawables() {
        drawables.clear();
        for (TikzComponent component : graph.getComponents()) {
            drawables.add(Drawer.toDrawable(component, view));
        }
    }

    public java.util.List<DrawableTikzComponent> getDrawables() {
        return drawables;
    }

    /**
     * Finds a component of the graph by its position
     *
     * @param position
     *            The position
     * @return a tikz component
     */
    public TikzComponent findComponentByPosition(final Point2D.Float position) {
        TikzComponent comp = null;

        for(int j = drawables.size() - 1; j >= 0; j--){
            DrawableTikzComponent draw = drawables.get(j);
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
     * @param position
     *            The position
     * @return A boolean value
     */

    public boolean hasComponentAtPosition(final Point2D.Float position) {
        if (findComponentByPosition(position) != null) {
            return true;
        }
        return false;
    }

    public Set<TikzComponent> getSelectedComponents(final Shape shape){
        for (DrawableTikzComponent draw: drawables){
            if (draw.intersects(shape)){
                selected_comp.add(draw.getComponent());
            }
        }
        return selected_comp;
    }

    public void unselectComponents() {
        selected_comp.clear();
    }

    /**
     * Adds a node to the graph
     *
     * @param component
     *            The component to be added
     * @param position
     *            The tikz position where the component will be added
     */

    public void addNodeToGraph(final TikzComponent component, final Point2D.Float position) {
        TikzNode node = (TikzNode) component;
        node.setPosition(Converter.swing2tikz(position, view));
        graph.add(node);
        view.resetTool();
    }

    /**
     * Adds an edge between two nodes of the graph
     *
     * @param edge
     *            The edge to be added
     * @param source
     *            The source node from which the edge will start
     * @param destination
     *            The destination node to which the edge will end
     */
    public void addEdgeToGraph(final TikzEdge edge, final TikzNode source, final TikzNode destination) {
        if ((source != null) && (destination != null)) {
            edge.setFirstNode(source);
            edge.setSecondNode(destination);
            graph.add(edge);
        }
    }

    /**
     * Adds an edge to the Canvas that will end at a given node given by its
     * position. Succeeds only if the position clicked is where a node exists
     *
     * @param component
     *            The edge to be added
     * @param position
     *            The position of the component to which the edge will end
     */
    public void addEdgeToModel(final TikzComponent component, final Point2D.Float position) {
        TikzComponent clickedComponent = findComponentByPosition(position);
        if (clickedComponent != null && !clickedComponent.isEdge()) {
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

    // Location has to be a swing position !
    public void addGraph(final TikzGraph g, final Point2D.Float location) {
        location.setLocation(Converter.swing2tikz(location, view));
        g.translation(location.x, location.y);
        graph.add(g);
    }

    // Location has to be a swing position !!
    public void moveComponent(final TikzComponent comp, final Point2D.Float location) {
        location.setLocation(Converter.swing2tikz(location, view));
        if (comp != null && comp.isNode()) {
            ((TikzNode) comp).setPosition(location);
            view.repaint();
        }
    }

    /**
     * Signals that the mouse is being clicked on
     *
     * @param e
     *            The MouseEvent
     * @param selectedTool
     *            The component that has been clicked on
     */

    public void mousePressed(final MouseEvent e, final TikzComponent selectedTool) {
        if (view.getIsFocused()) {
            if (selectedTool != null && selectedTool.isNode()) {
                this.addNodeToGraph(selectedTool, new Point2D.Float(e.getX(), e.getY()));
            } else if (selectedTool != null && selectedTool.isEdge()) {
                this.addEdgeToModel(selectedTool, new Point2D.Float(e.getX(), e.getY()));
            }
        } else {
            view.requestFocusInWindow();
        }
    }

    /**
     * Signals that the mouse click has been dropped
     *
     * @param transfer_data
     *            The component associated with the click
     * @param location
     *            The position of the pointer where the mouse click has been
     *            dropped
     */

    public void mouseDropped(final TransferTikz transfer_data, final Point2D.Float location) {
        switch (transfer_data.getOption()) {
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
     * Sets the focus of the view at true meaning that the user has clicked in
     * the canvas area, repaints the graph
     */
    public void focusGained() {
        view.setIsFocused(true);
        view.repaint();
    }

    /**
     * Sets the focus of the view at false meaning that the user has clicked out
     * of the canvas area, repaints the graph
     */
    public void focusLost() {
        view.setIsFocused(false);
        view.repaint();
    }

    /**
     * Adds a drawable tikz component to the drawables set
     *
     * @param draw
     *            The DrawableTikzComponent to be added
     */
    public void addDrawableComponent(final DrawableTikzComponent draw) {
        drawables.add(draw);
    }

    /**
     * Delete an item from the model.
     *
     * @param itemToDelete
     *            the item that will be removed.
     */

    public void deleteItem(final TikzComponent itemToDelete) {
        if (itemToDelete != null) {
            if (itemToDelete.isNode()) {
                this.graph.remove((TikzNode) itemToDelete);
            } else {
                this.graph.remove((TikzEdge) itemToDelete);
            }
        }
    }

    public void editItem(final TikzComponent itemToEdit) {
        new NodeEditionView(itemToEdit);
    }

    /**
     * Export a collection of TikzComponents as a Template.
     *
     * A Template object is composed by a TikzGraph and a File. This Template
     * will be directly saved in the file defined by the Template's File
     * attribute.
     */
    public void exportSelectionAsTemplate() {
        CanvasSelection selection = view.getSelection();
        if (selection != null) {
            Set<TikzComponent> components = getSelectedComponents(selection.getSelectionRectangle());
            try {
                if (!components.isEmpty()) {
                    File file = TemplateIOManager.exportGraphAsTemplate(components);
                    unselectComponents();
                    view.addTemplateToParentView(file);
                }
            } catch (IOException e) {
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
     * Constructs a new Canvas state with an empty component and related
     * component
     */
    public CanvasState() {
        component = null;
        related_component = null;
    }

    /**
     * Constructs a new Canvas state with two given tikz components
     *
     * @param comp
     *            The component
     * @param pos
     *            The related component
     */
    public CanvasState(final TikzComponent comp, final TikzComponent pos) {
        component = comp;
        related_component = pos;
    }

    /**
     * Compare whether a given canvas state is equals to this canvas state
     *
     * @param o_state
     *            The other canvas state to be compared with
     * @return whether the given canvas state is equals to this canavs state
     */
    public boolean equalsTo(final CanvasState o_state) {
        return ((o_state.getComponent() == this.component) && (o_state.getRelatedComponent() == this.getRelatedComponent()));
    }

    /**
     * Returns true if the component and the related component are both
     * initialized
     *
     * @return true if the component and the related component are both not null
     */
    public boolean initialized() {
        return ((component != null) && (related_component != null));
    }

    /**
     * Returns true if the component is equals to the specified component
     *
     * @param comp
     *            the component to be compared with
     * @return true if the component and the specified component are equals
     */
    public boolean componentEqualsTo(final TikzComponent comp) {
        return comp == this.component;
    }

    /**
     * Getters for the component
     *
     * @return the component
     */
    public TikzComponent getComponent() {
        return component;
    }

    /**
     * Setter for the componenet
     *
     * @param component
     *            the component to be set with
     */
    public void setComponent(final TikzComponent component) {
        this.component = component;
    }

    /**
     * Getter for the relative component
     *
     * @return The relative component
     */
    public TikzComponent getRelatedComponent() {
        return related_component;
    }

    /**
     * Setter for the relative component
     *
     * @param related_component
     *            the relative component to be set with
     */
    public void setRelatedComponent(final TikzComponent related_component) {
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
