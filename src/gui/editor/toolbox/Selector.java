package gui.editor.toolbox;

import javax.swing.*;
import java.awt.*;
import gui.editor.views.canvas.drawers.CircleDrawer;
import gui.editor.views.canvas.drawers.RectangleDrawer;
import gui.editor.views.canvas.drawers.TriangleDrawer;
import models.TikzCircle;
import models.TikzRectangle;
import models.TikzTriangle;

/**
 * Created by aurelien on 12/04/16.
 */
public class Selector extends JPanel {

    private static final int SHAPE_SIZE = 100;

    private JScrollPane scrollzone;
    private JPanel options;
    private SelectorComponent circle_panel;
    private SelectorComponent rectangle_panel;
    private SelectorComponent triangle_panel;

    public Selector(int componentsNbr) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        options = new JPanel(new GridLayout(componentsNbr, 0));
        scrollzone = new JScrollPane(options);
        this.add(scrollzone);

        addCircle();
        addRectangle();
        addTriangle();
    }

    private void addCircle(){

        TikzCircle circle = new TikzCircle();
        circle.setRadius(SHAPE_SIZE/2);
        circle_panel = new SelectorComponent(new CircleDrawer(circle), circle);
        options.add(circle_panel);
    }

    private void addRectangle(){

        TikzRectangle rectangle = new TikzRectangle(SHAPE_SIZE, SHAPE_SIZE);
        rectangle_panel = new SelectorComponent(new RectangleDrawer(rectangle), rectangle);
        options.add(rectangle_panel);
    }

    private void addTriangle(){

        TikzTriangle triangle = new TikzTriangle();
        triangle.setEquilateral(SHAPE_SIZE/2);
        triangle_panel = new SelectorComponent(new TriangleDrawer(triangle), triangle);
        options.add(triangle_panel);
    }
}
