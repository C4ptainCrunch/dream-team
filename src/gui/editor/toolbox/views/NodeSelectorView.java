package gui.editor.toolbox.views;

import models.tikz.TikzCircle;
import models.tikz.TikzRectangle;
import models.tikz.TikzTriangle;
import gui.editor.toolbox.SelectorComponent;
import gui.editor.toolbox.model.ToolModel;

public class NodeSelectorView extends SelectorView {
    private static final int SHAPE_SIZE = 100;

    public NodeSelectorView(ToolModel model) {
        super(model);
        this.setComponentNbr(3);
        this.initComponents();
    }

    private void initComponents() {
        addCircle();
        addRectangle();
        addTriangle();
    }

    private void addCircle() {
        TikzCircle circle = new TikzCircle();
        circle.setRadius(SHAPE_SIZE / 2);
        this.addComponent(new SelectorComponent(circle, this));
    }

    private void addRectangle() {
        TikzRectangle rectangle = new TikzRectangle(SHAPE_SIZE, SHAPE_SIZE);
        this.addComponent(new SelectorComponent(rectangle, this));
    }

    private void addTriangle() {
        TikzTriangle triangle = new TikzTriangle();
        triangle.setEquilateral(SHAPE_SIZE);
        this.addComponent(new SelectorComponent(triangle, this));
    }
}
