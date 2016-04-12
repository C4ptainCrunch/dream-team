package gui.editor.toolbox;

import javax.swing.*;
import java.awt.*;
import gui.editor.views.canvas.drawers.CircleDrawer;
import gui.editor.views.canvas.drawers.RectangleDrawer;
import models.TikzCircle;
import models.TikzRectangle;

/**
 * Created by aurelien on 12/04/16.
 */
public class Selector extends JPanel {

    private static final int SHAPE_SIZE = 50;

    private JScrollPane scrollzone;
    private JPanel options;
    private SelectorComponent circle_panel;
    private SelectorComponent rectangle_panel;

    public Selector(int componentsNbr) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        options = new JPanel(new GridLayout(componentsNbr, 0));
        scrollzone = new JScrollPane(options);
        this.add(scrollzone);

        addCircle();
        addRectangle();
    }

    private void addCircle(){

        TikzCircle circle = new TikzCircle();
        circle.setRadius(SHAPE_SIZE);
        circle_panel = new SelectorComponent(new CircleDrawer(circle));
        options.add(circle_panel);
    }

    private void addRectangle(){

        TikzRectangle rectangle = new TikzRectangle(SHAPE_SIZE*2, SHAPE_SIZE*2);
        rectangle_panel = new SelectorComponent(new RectangleDrawer(rectangle));
        options.add(rectangle_panel);
    }
}
