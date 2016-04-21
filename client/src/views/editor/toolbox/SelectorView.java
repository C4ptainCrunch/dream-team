package views.editor.toolbox;

import java.awt.*;

import javax.swing.*;

import misc.SelectorComponent;
import models.ToolModel;
import models.tikz.TikzComponent;
import controllers.editor.toolbox.SelectorController;

/**
 * Implementation of the View (from the MVC architectural pattern) for the
 * Selector. The Selector is part of the toolbox used to choose which component
 * is selected.
 */
public class SelectorView extends JPanel implements SelectorComponent.SelectorComponentListener {
    private final JScrollPane scrollzone;
    private final JPanel options;
    private final SelectorController controller;
    private final ToolView parentView;

    /**
     * Constructs a new view for the Selector with a given ToolModel
     *
     * @param parentView
     * @param model
     */
    public SelectorView(ToolView parentView, ToolModel model) {
        this.parentView = parentView;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        options = new JPanel(new GridLayout(1, 0));
        scrollzone = new JScrollPane(options);
        this.add(scrollzone);
        controller = new SelectorController(this, model);
    }

    /**
     * Adds a SelectorComponent (tikz element) to this view
     *
     * @param comp
     *            The component
     */
    protected void addComponent(SelectorComponent comp) {
        options.add(comp);
    }

    /**
     * Set the number of components contained in the view
     *
     * @param nbr
     *            The number of components
     */
    public void setComponentNbr(int nbr) {
        ((GridLayout) options.getLayout()).setRows(nbr);
    }

    /**
     * Informs the controller which component is being selected
     *
     * @param component
     *            The selected component
     */
    @Override
    public void componentSelected(TikzComponent component) {
        controller.itemSelected(component);
    }

    public ToolView getParentView() {
        return parentView;
    }
}
