package models;

import java.awt.*;
import java.util.Observable;

import models.tikz.TikzComponent;

/**
 * Implementation of the Model (from the MVC architectural pattern) for the ToolBox.
 */
public class ToolModel extends Observable {

    private TikzComponent component;

    public ToolModel() {
        this.component = null;
    }

    public ToolModel(TikzComponent component) {
        this.component = component;
    }

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
        if (component != null) {
            component.setLabel(label);
            alertObservers();
        }
    }

    /**
     * Setter for the component's color,
     * notifies observers
     * @param color The color
     */
    public void setComponentColor(Color color) {
        if (component != null) {
            component.setColor(color);
            alertObservers();
        }
    }

    /**
     * Getter for the component's clone
     * @return The clone of the component
     */

    public TikzComponent getComponentClone() {
        if (component == null){
            return component;
        }
        return component.getClone();
    }

    public void reset(){
        component = null;
        alertObservers();
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
        if (component != null) {
            component.setStroke(width);
            alertObservers();
        }
    }

}
