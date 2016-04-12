package gui.editor.toolbox;

import gui.editor.views.canvas.drawers.CircleDrawer;
import gui.editor.views.canvas.drawers.RectangleDrawer;
import gui.editor.views.canvas.drawers.TriangleDrawer;
import models.TikzCircle;
import models.TikzRectangle;
import models.TikzTriangle;

/**
 * Created by jhellinckx on 12/04/16.
 */
public class NodeSelector extends Selector {
    private static final int SHAPE_SIZE = 100;
    public NodeSelector(SelectorListener lis){
        super(lis);
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
