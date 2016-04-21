package views.editor.toolbox;

import misc.SelectorComponent;
import models.ToolModel;
import models.tikz.TikzCircle;
import models.tikz.TikzRectangle;

/**
 * Implementation of the View (from the MVC architectural pattern) for the
 * NodeSelector. The NodeSelector is part of the toolbox used to choose the type
 * of node.
 */
public class NodeSelectorView extends SelectorView {
    private static final int SHAPE_SIZE = 100;

    /**
     * Constructs a new view for the NodeSelector with a given ToolModel
     *
     * @param model
     *            the tool model
     */
    public NodeSelectorView(ToolView parentView, ToolModel model) {
        super(parentView, model);
        this.setComponentNbr(3);
        this.initComponents();
    }

    /**
     * Initializes the selectable components
     */
    private void initComponents() {
        addCircle();
        addRectangle();
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
}
