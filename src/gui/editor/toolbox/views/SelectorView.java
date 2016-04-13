package gui.editor.toolbox.views;

import gui.editor.toolbox.SelectorComponent;
import gui.editor.toolbox.controllers.SelectorController;
import gui.editor.toolbox.model.ToolModel;
import gui.editor.views.canvas.drawers.ComponentDrawer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by aurelien on 12/04/16.
 */
public class SelectorView extends JPanel implements SelectorComponent.SelectorComponentListener {
    private JScrollPane scrollzone;
    private JPanel options;
    private SelectorController controller;

    public SelectorView(ToolModel model) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        options = new JPanel(new GridLayout(1, 0));
        scrollzone = new JScrollPane(options);
        this.add(scrollzone);
        controller = new SelectorController(this, model);
    }

    protected void addComponent(SelectorComponent comp){
        options.add(comp);
    }

    public void setComponentNbr(int nbr){
        ((GridLayout)options.getLayout()).setRows(nbr);
    }

    @Override
    public void componentSelected(ComponentDrawer drawer){
        controller.itemSelected(drawer);

    }
}
