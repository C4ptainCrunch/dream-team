package models.tikz;

import java.awt.*;
import java.util.Observable;
import java.util.UUID;

import constants.Models;
import parser.TikzFormatter;

/**
 * This abstract class defines the common elements of a tikz component (node,
 * ..)
 */
public abstract class TikzComponent extends Observable {
    private Color color;
    private String label;
    private int stroke;
    private String reference;

    /**
     * Constructs a default tikz component
     */
    protected TikzComponent() {
        this(UUID.randomUUID().toString());
    }

    /**
     * Constructs a default tikz component with a given reference
     *
     * @param reference
     *            the reverence for the component
     */
    protected TikzComponent(String reference) {
        this.color = Models.DEFAULT.COLOR;
        this.label = Models.DEFAULT.LABEL;
        this.stroke = Models.DEFAULT.STROKE;
        this.reference = reference;
    }

    /**
     * Constructs a tikz component by copying an other tikz component
     *
     * @param o_comp
     *            The tikz compmonent to be copied from
     */
    protected TikzComponent(TikzComponent o_comp) {
        this(o_comp, UUID.randomUUID().toString());
    }

    /**
     * Constructs a tikz component by copying an other tikz component with a
     * given reference
     *
     * @param o_comp
     *            The tikz compmonent to be copied from
     * @param reference
     *            the reference for the component
     */
    protected TikzComponent(TikzComponent o_comp, String reference) {
        this.color = o_comp.getColor();
        this.label = o_comp.getLabel();
        this.stroke = o_comp.getStroke();
        this.reference = reference;
    }

    /**
     * Getter for the color of this tikz component
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter for the color of this tikz component
     *
     * @param color
     *            The color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Getter for the label of this tikz component
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Setter for the label of this tikz component
     *
     * @param label
     *            The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter for the stroke of this tikz component
     *
     * @return the stroke
     */
    public int getStroke() {
        return this.stroke;
    }

    /**
     * Setter for the stroke of this tikz component
     *
     * @param stroke
     *            The stroke
     */
    public void setStroke(int stroke) {
        this.stroke = stroke;
    }

    /**
     * Abstract method that needs to be redefined in classes that extends this
     * class. Getter for a clone (ie. copy of the current object)
     *
     * @return A new object that is the copy of the current object
     */
    public abstract TikzComponent getClone();

    /**
     * Getter for the reference of this tikz component
     *
     * @return the reference
     */
    public String getReference() {
        return this.reference;
    }

    public void setReference(String ref) {
        this.reference = ref;
    }

    public boolean isNode() {
        return false;
    }

    public boolean isEdge() {
        return false;
    }

    public String toString() {
        return TikzFormatter.format(this);
    }
}
