package models.tikz;

import parser.TikzFormatter;
import constants.Models;

/**
 * Implementation of the Rectangle Model (from the MVC architectural pattern)
 * This class represents a rectangle that is linked to a tikz code
 */
public class TikzRectangle extends TikzShape {
    private float width;
    private float length;

    /**
     * Constructs a default tikz rectangle
     */
    public TikzRectangle() {
        super();
        width = Models.DEFAULT.LENGTH;
        length = Models.DEFAULT.LENGTH;
    }

    /**
     * Constructs a tikz rectangle with a given width and length
     *  @param width
     *            The width of the rectangle
     * @param length
     */
    public TikzRectangle(float width, float length) {
        super();
        setWidth(width);
        setLength(length);
    }

    /**
     * Constructs a tikz rectangle by copying an other tikz rectangle
     *
     * @param o_rectangle
     *            The tikz rectangle to be copied from
     */
    public TikzRectangle(TikzRectangle o_rectangle) {
        super(o_rectangle);
        width = o_rectangle.getWidth();
        length = o_rectangle.getLength();
    }

    /**
     * Constructs a default tikz rectangle with a given reference
     *
     * @param reference
     *            the reference
     */
    public TikzRectangle(String reference) {
        super(reference);
        width = Models.DEFAULT.LENGTH;
        length = Models.DEFAULT.LENGTH;
    }

    /**
     * Constructs a tikz rectangle with a given width and length and reference
     *
     * @param width
     *            The width of the rectangle
     * @param length
     *            The length of the rectangle
     * @param reference
     *            the reference
     */
    public TikzRectangle(float width, float length, String reference) {
        super(reference);
        setWidth(width);
        setLength(length);
    }

    /**
     * Constructs a tikz rectangle by copying an other tikz rectangle with a
     * given reference
     *
     * @param o_rectangle
     *            The tikz rectangle to be copied from
     * @param reference
     *            the reference
     */
    public TikzRectangle(TikzRectangle o_rectangle, String reference) {
        super(o_rectangle, reference);
        width = o_rectangle.getWidth();
        length = o_rectangle.getLength();
    }

    /**
     * Getter for the width of the tikz rectangle
     *
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * Setter of the width of the tikz rectangle
     *
     * @param width
     *            The width
     */
    public void setWidth(float width) {
        this.width = width;
        setChanged();
        notifyObservers();
    }

    /**
     * Getter for the length of the tikz rectangle
     *
     * @return the length
     */
    public float getLength() {
        return length;
    }

    /**
     * Setter for the length of the tikz rectangle
     *
     * @param length
     *            The length
     */
    public void setLength(float length) {
        this.length = length;
        setChanged();
        notifyObservers();
    }


    /**
     * Getter for a clone (ie. copy of the current tikz rectangle)
     *
     * @return A new tikz rectangle that is the copy of the current tikz
     *         rectangle
     */
    @Override
    public TikzRectangle getClone() {
        return new TikzRectangle(this);
    }

    @Override
    public String toString(){
        return TikzFormatter.format(this);
    }

    @Override
    public boolean isRectangle() {
        return true;
    }

}
