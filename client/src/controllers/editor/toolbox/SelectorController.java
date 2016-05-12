package controllers.editor.toolbox;

import java.util.Observable;
import java.util.Observer;

import models.ToolModel;
import models.tikz.TikzComponent;
import views.editor.toolbox.AttributesChooserView;
import views.editor.toolbox.SelectorView;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the
 * Selector. The Selector is part of the toolbox used to choose which component
 * is selected.
 */
public class SelectorController implements Observer {

    private final SelectorView view;
    private final ToolModel model;

    /**
     * Constructs a new Controller for the Selector with a given SelectorView
     * and a ToolModel
     *
     * @param v
     *            The selector view which is associated with this controller
     * @param m
     *            The tool model
     */
    public SelectorController(final SelectorView v, final ToolModel m) {
        view = v;
        model = m;
    }

    /**
     * Informs the model that a component is being selected
     *
     * @param component
     *            The component being selected
     */
    public void itemSelected(final TikzComponent component) {
        model.setComponent(component);

        AttributesChooserView attributes = view.getParentView().getAttributesChooserView();
        model.setComponentColor(attributes.getColor());
        model.setComponentLabel(attributes.getLabel());
        model.setComponentStrokeWidth(attributes.getStrokeWidth());
    }

    /**
     * Called when Observables linked to this Observer call notify(),
     *
     * @param o
     *            The Observable
     * @param obj
     *            The Object given by the Observable
     */
    @Override
    public void update(final Observable o, final Object obj) {
        // this was left intentionally blank
    }
}
