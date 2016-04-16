package gui.editor.toolbox.views;

import models.TikzDirectedEdge;
import models.TikzUndirectedEdge;
import gui.editor.toolbox.SelectorComponent;
import gui.editor.toolbox.model.ToolModel;

/**
 * Implementation of the View (from the MVC architectural pattern) for the EdgeSelector.
 * The EdgeSelector is part of the toolbox used to choose
 * the type of edge.
 */
public class EdgeSelectorView extends SelectorView {

    /**
     * Constructs a new view for the EdgeSelector
     * with a given ToolModel
     * @param model the tool model
     */
    public EdgeSelectorView(ToolModel model) {
        super(model);
        this.setComponentNbr(2);
        this.initComponents();
    }

    /**
     * Initializes the edges
     */
    private void initComponents() {
        addUndirectedEdge();
        addDirectedEdge();
    }

    /**
     * Adds an undirected edge
     */
    private void addUndirectedEdge() {
        this.addComponent(new SelectorComponent(new TikzUndirectedEdge(), this));
    }

    /**
     * Adds a directed edge
     */
    private void addDirectedEdge() {
        this.addComponent(new SelectorComponent(new TikzDirectedEdge(), this));
    }
}
