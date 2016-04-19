package views.editor.toolbox;

import java.awt.*;

import javax.swing.*;

import models.tikz.TikzComponent;
import misc.SelectorComponent;
import controllers.editor.toolbox.SelectorController;
import models.ToolModel;

/**
 * Implementation of the View (from the MVC architectural pattern) for the Selector.
 * The Selector is part of the toolbox used to choose
 * which component is selected.
 */
public class SelectorView extends JPanel implements SelectorComponent.SelectorComponentListener {
    private final JScrollPane scrollzone;
    private final JPanel options;
    private final SelectorController controller;

    /**
     * Constructs a new view for the Selector
     * with a given ToolModel
     * @param model the tool model
     */
    public SelectorView(ToolModel model) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        options = new JPanel(new GridLayout(1, 0));
        scrollzone = new JScrollPane(options);
        this.add(scrollzone);
        controller = new SelectorController(this, model);
    }

    /**
     * Adds a SelectorComponent (tikz element) to this view
     * @param comp The component
     */
    protected void addComponent(SelectorComponent comp) {
        options.add(comp);
    }

    /**
     * Set the number of components contained in the view
     * @param nbr The number of components
     */
    public void setComponentNbr(int nbr) {
        ((GridLayout) options.getLayout()).setRows(nbr);
    }

    /**
     * Informs the controller which component is being selected
     * @param component The selected component
     */
    @Override
    public void componentSelected(TikzComponent component) {
        controller.itemSelected(component);
    }
}
