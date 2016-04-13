package gui.editor.toolbox.views;

import gui.editor.toolbox.SelectorComponent;
import gui.editor.toolbox.model.ToolModel;
import gui.editor.views.canvas.drawers.CircleDrawer;
import gui.editor.views.canvas.drawers.RectangleDrawer;
import gui.editor.views.canvas.drawers.TriangleDrawer;
import models.TikzCircle;
import models.TikzRectangle;
import models.TikzTriangle;

public class NodeSelectorView extends SelectorView {
    private static final int SHAPE_SIZE = 100;
    public NodeSelectorView(ToolModel model){
        super(model);
        this.setComponentNbr(3);
        this.initComponents();
    }

    private void initComponents(){
        addCircle();
        addRectangle();
        addTriangle();
    }

    private void addCircle(){
        TikzCircle circle = new TikzCircle();
        circle.setRadius(SHAPE_SIZE/2);
        this.addComponent(new SelectorComponent(new CircleDrawer(circle), circle, this));
    }

    private void addRectangle(){
        TikzRectangle rectangle = new TikzRectangle(SHAPE_SIZE, SHAPE_SIZE);
        this.addComponent(new SelectorComponent(new RectangleDrawer(rectangle), rectangle, this));
    }

    private void addTriangle(){
        TikzTriangle triangle = new TikzTriangle();
        triangle.setEquilateral(SHAPE_SIZE);
        this.addComponent(new SelectorComponent(new TriangleDrawer(triangle), triangle, this));
    }
}
