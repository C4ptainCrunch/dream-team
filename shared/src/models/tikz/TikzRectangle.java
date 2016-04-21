package models.tikz;

import constants.Models;

/**
 * Implementation of the Rectangle Model (from the MVC architectural pattern)
 * This class represents a rectangle that is linked to a tikz code
 */
public class TikzRectangle extends TikzShape {
    private int width;
    private int length;

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
     *
     * @param width
     *            The width of the rectangle
     * @param length
     *            The length of the rectangle
     */
    public TikzRectangle(int width, int length) {
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
    public TikzRectangle(int width, int length, String reference) {
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
    public int getWidth() {
        return width;
    }

    /**
     * Setter of the width of the tikz rectangle
     *
     * @param width
     *            The width
     */
    public void setWidth(int width) {
        this.width = width;
        setChanged();
        notifyObservers();
    }

    /**
     * Getter for the length of the tikz rectangle
     *
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * Setter for the length of the tikz rectangle
     *
     * @param length
     *            The length
     */
    public void setLength(int length) {
        this.length = length;
        setChanged();
        notifyObservers();
    }

    /**
     * Transforms this tikz rectangle into tikz code string
     *
     * @return The tikz code string
     */
    @Override
    public String toString() {
        String options = String.join(", ", new String[] { "rectangle" }); // TODO:
                                                                          // do
                                                                          // this
        if (!options.contains("draw")) {
            options += ", draw";
        }
        return String.format("\\node[%s](%s) at (%.0f,%.0f){%s};\n", options, "", getPosition().getX(), getPosition().getY(), getLabel());
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
}
