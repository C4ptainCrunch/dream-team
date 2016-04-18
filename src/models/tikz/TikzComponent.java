package models.tikz;

import java.awt.*;
import java.util.Observable;

import constants.Models;

/**
 * This abstract class defines the common elements of a tikz component (node, ..)
 */
public abstract class TikzComponent extends Observable {
    private Color color;
    private String label;
    private int stroke;

    /**
     * Constructs a default tikz component
     */
    protected TikzComponent() {
        this.color = Models.DEFAULT.COLOR;
        this.label = Models.DEFAULT.LABEL;
        this.stroke = Models.DEFAULT.STROKE;
    }

    /**
     * Constructs a tikz component by copying an other tikz component
     * @param o_comp The tikz compmonent to be copied from
     */
    protected TikzComponent(TikzComponent o_comp) {
        this.color = o_comp.getColor();
        this.label = o_comp.getLabel();
        this.stroke = o_comp.getStroke();
    }

    /**
     * Getter for the color of this tikz component
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter for the color of this tikz component
     * @param color The color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Getter for the label of this tikz component
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Setter for the label of this tikz component
     * @param label The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter for the stroke of this tikz component
     * @return the stroke
     */
    public int getStroke() {
        return this.stroke;
    }

    /**
     * Setter for the stroke of this tikz component
     * @param stroke The stroke
     */
    public void setStroke(int stroke) {
        this.stroke = stroke;
    }

    /**
     * Abstract method that needs to be redefined in classes
     * that extends this class.
     * Getter for a clone (ie. copy of the current object)
     * @return A new object that is the copy of the current object
     */
    public abstract TikzComponent getClone();
}
