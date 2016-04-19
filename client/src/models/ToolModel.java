package models;

import java.awt.*;
import java.util.Observable;

import models.tikz.TikzComponent;

/**
 * Implementation of the Model (from the MVC architectural pattern) for the ToolBox.
 */
public class ToolModel extends Observable {

    private TikzComponent component;

    /**
     * Notifies the Observers that are linked to this Observable
     * with a given tkz component
     */
    private void alertObservers() {
        this.setChanged();
        notifyObservers(component);
    }

    /**
     * Setter for the component's label,
     * notifies observers
     * @param label The label
     */
    public void setComponentLabel(String label) {
        component.setLabel(label);
        alertObservers();
    }

    /**
     * Setter for the component's color,
     * notifies observers
     * @param color The color
     */
    public void setComponentColor(Color color) {
        component.setColor(color);
        alertObservers();
    }

    /**
     * Getter for the component
     * @return the tikz component
     */
    public TikzComponent getComponent() {
        return component;
    }

    /**
     * Setter for the component
     * @param component the tikz component to be set with
     */
    public void setComponent(TikzComponent component) {
        this.component = component.getClone();
        alertObservers();
    }

    /**
     * Setter for the component's width,
     * notifies observers
     * @param width The width
     */
    public void setComponentStrokeWidth(int width) {
        component.setStroke(width);
        alertObservers();
    }

}
