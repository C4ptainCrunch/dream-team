package controllers.editor.toolbox;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import models.ToolModel;
import views.editor.toolbox.AttributesChooserView;
import views.editor.toolbox.TikzColorChooser;

/**
 * Implementation of the Controller (from the MVC architectural pattern) for the
 * AttributesChooser. The AttributesChooser is part of the toolbox used to
 * choose attributes for a particular component.
 */
public class AttributesChooserController implements Observer {

    private static final String COLOR_DIALOG_TITLE = "Choose Stroke Color";

    private final AttributesChooserView view;
    private final ToolModel model;
    private Color chosen_color;

    /**
     * Constructs a new Controller for the AttributesChooser with a given
     * AttributesChooserView and a ToolModel
     *
     * @param v
     *            The attributes chooser view which is associated with this
     *            controller
     * @param m
     *            The tool model
     */
    public AttributesChooserController(final AttributesChooserView v, final ToolModel m) {
        view = v;
        model = m;
    }

    /**
     * Sets the color that is selected to the tool model
     *
     * @param color
     *            The color
     */
    private void colorSelected(final Color color) {
        model.setComponentColor(color);
    }

    /**
     * Sets the label that is selected to the tool model
     */
    public void labelEntered() {
        model.setComponentLabel(view.getLabel());
    }

    /**
     * Sets the width that is selected to the tool model
     */
    public void strokeChanged() {
        int width = view.getStrokeWidth();
        model.setComponentStrokeWidth(width);
    }

    /**
     * Opens a dialog window that displays colors that can be selected, sets the
     * selected color to the tool model
     */
    public void chooseColor() {
        chosen_color = TikzColorChooser.choose(view.getColor());
        view.setColorFieldColor(chosen_color);
        colorSelected(chosen_color);
    }

    /**
     * Called when Observables linked to this Observer call notify(), repaints
     * the associated view
     *
     * @param o
     *            The Observable
     * @param obj
     *            The Object given by the Observable
     */
    @Override
    public void update(final Observable o, final Object obj) {
        view.repaint();
    }
}
