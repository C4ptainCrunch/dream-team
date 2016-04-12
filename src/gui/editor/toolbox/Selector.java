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
    private JScrollPane scrollzone;
    private JPanel options;

    public Selector() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        options = new JPanel(new GridLayout(1, 0));
        scrollzone = new JScrollPane(options);
        this.add(scrollzone);
    }

    protected void addComponent(SelectorComponent comp){
        options.add(comp);
    }

    public void setComponentNbr(int nbr){
        ((GridLayout)options.getLayout()).setRows(nbr);
    }
}
