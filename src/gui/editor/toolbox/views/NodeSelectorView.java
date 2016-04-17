package gui.editor.toolbox.views;

import models.TikzCircle;
import models.TikzRectangle;
import models.TikzTriangle;
import gui.editor.toolbox.SelectorComponent;
import gui.editor.toolbox.model.ToolModel;

/**
 * Implementation of the View (from the MVC architectural pattern) for the NodeSelector.
 * The NodeSelector is part of the toolbox used to choose
 * the type of node.
 */
public class NodeSelectorView extends SelectorView {
    private static final int SHAPE_SIZE = 100;

    /**
     * Constructs a new view for the NodeSelector
     * with a given ToolModel
     * @param model the tool model
     */
    public NodeSelectorView(ToolModel model) {
        super(model);
        this.setComponentNbr(3);
        this.initComponents();
    }

    /**
     * Initializes the selectable components
     */
    private void initComponents() {
        addCircle();
        addRectangle();
        addTriangle();
    }

    /**
     * Add the circle components to be selectable
     */
    private void addCircle() {
        TikzCircle circle = new TikzCircle();
        circle.setRadius(SHAPE_SIZE / 2);
        this.addComponent(new SelectorComponent(circle, this));
    }

    /**
     * Add the rectangle components to be selectable
     */
    private void addRectangle() {
        TikzRectangle rectangle = new TikzRectangle(SHAPE_SIZE, SHAPE_SIZE);
        this.addComponent(new SelectorComponent(rectangle, this));
    }

    /**
     * Add the triangle components to be selectable
     */
    private void addTriangle() {
        TikzTriangle triangle = new TikzTriangle();
        triangle.setEquilateral(SHAPE_SIZE);
        this.addComponent(new SelectorComponent(triangle, this));
    }
}
