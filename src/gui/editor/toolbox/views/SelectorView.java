package gui.editor.toolbox.views;

import java.awt.*;

import javax.swing.*;

import models.tikz.TikzComponent;
import gui.editor.toolbox.SelectorComponent;
import gui.editor.toolbox.controllers.SelectorController;
import gui.editor.toolbox.model.ToolModel;

/**
 * Created by aurelien on 12/04/16.
 */
public class SelectorView extends JPanel implements SelectorComponent.SelectorComponentListener {
    private final JScrollPane scrollzone;
    private final JPanel options;
    private final SelectorController controller;

    public SelectorView(ToolModel model) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        options = new JPanel(new GridLayout(1, 0));
        scrollzone = new JScrollPane(options);
        this.add(scrollzone);
        controller = new SelectorController(this, model);
    }

    protected void addComponent(SelectorComponent comp) {
        options.add(comp);
    }

    public void setComponentNbr(int nbr) {
        ((GridLayout) options.getLayout()).setRows(nbr);
    }

    @Override
    public void componentSelected(TikzComponent component) {
        controller.itemSelected(component);
    }
}
