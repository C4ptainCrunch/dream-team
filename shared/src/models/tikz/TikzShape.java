package models.tikz;

import java.awt.*;

import constants.Models;

/**
 * This abstract class defines the common elements of a tikz shape (circle,
 * rectangle, ..)
 */
public abstract class TikzShape extends TikzNode {
    private Color backgroundColor;

    /**
     * Constructs a default tikz shape
     */
    protected TikzShape() {
        super();
        backgroundColor = Models.DEFAULT.BACKGROUND_COLOR;
    }

    /**
     * Constructs a tikz shape by copying an other tikz shape
     *
     * @param o_shape
     *            The tikz shape to be copied from
     */
    protected TikzShape(TikzShape o_shape) {
        super(o_shape);
        backgroundColor = o_shape.getBackgroundColor();
    }

    /**
     * Constructs a default tikz shape with a given reference
     *
     * @param reference
     *            the reference
     */
    protected TikzShape(String reference) {
        super(reference);
        backgroundColor = Models.DEFAULT.BACKGROUND_COLOR;
    }

    /**
     * Constructs a tikz shape by copying an other tikz shape with a given
     * reference
     *
     * @param o_shape
     *            The tikz shape to be copied from
     * @param reference
     *            the reference
     */
    protected TikzShape(TikzShape o_shape, String reference) {
        super(o_shape, reference);
        backgroundColor = o_shape.getBackgroundColor();
    }

    /**
     * Getter for the background color
     *
     * @return the background color
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color color) {
        backgroundColor = color;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Abstract method that needs to be redefined in classes that extends this
     * class. Getter for a clone (ie. copy of the current object)
     *
     * @return A new object that is the copy of the current object
     */
    public abstract TikzShape getClone();

    public boolean isShape() {
        return true;
    }

    public boolean isCircle() {
        return false;
    }

    public boolean isRectangle() {
        return false;
    }

    public boolean isPolygon() {
        return false;
    }
}
