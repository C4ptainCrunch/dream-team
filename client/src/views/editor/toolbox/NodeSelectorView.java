package views.editor.toolbox;

import misc.SelectorComponent;
import models.ToolModel;
import models.tikz.TikzCircle;
import models.tikz.TikzPolygon;
import models.tikz.TikzRectangle;
import constants.Models;

/**
 * Implementation of the View (from the MVC architectural pattern) for the
 * NodeSelector. The NodeSelector is part of the toolbox used to choose the type
 * of node.
 */
public class NodeSelectorView extends SelectorView {

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
        addTriangle();
    }

    /**
     * Add the circle components to be selectable
     */
    private void addCircle() {
        TikzCircle circle = new TikzCircle();
        circle.setRadius(Models.DEFAULT.LENGTH / 2);
        this.addComponent(new SelectorComponent(circle, this));
    }

    /**
     * Add the rectangle components to be selectable
     */
    private void addRectangle() {
        TikzRectangle rectangle = new TikzRectangle(Models.DEFAULT.LENGTH, Models.DEFAULT.LENGTH);
        this.addComponent(new SelectorComponent(rectangle, this));
    }

    private void addTriangle() {
        TikzPolygon triangle = new TikzPolygon(Models.DEFAULT.LENGTH / 2, 3);
        this.addComponent(new SelectorComponent(triangle, this));
    }
}
