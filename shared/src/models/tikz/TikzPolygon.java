package models.tikz;

import constants.Models;
import parser.TikzFormatter;

/**
 * Implementation of the Polygon Model (from the MVC architectural pattern) This
 * class represents a polygon that is linked to a tikz code. A polygon consists
 * of a length and a number of sides. The length of on side is equals to the
 * length of all the other sides.
 */
public class TikzPolygon extends TikzShape {
    private int length;
    private int sides;

    /**
     * Constructs a default polygon
     */
    public TikzPolygon() {
        super();
        length = Models.DEFAULT.LENGTH;
        sides = Models.DEFAULT.SIDES;
    }

    /**
     * Constructs a polygon with a given length and a given number of sides.
     *
     * @param length
     *            The length of one side
     * @param sides
     *            The number of sides
     */
    public TikzPolygon(int length, int sides) {
        super();
        this.length = length;
        this.sides = sides;
    }

    /**
     * Constructs a tikz polygon by copying an other tikz polygon
     *
     * @param o_polygon
     *            The tikz polygon to be copied from
     */
    public TikzPolygon(TikzPolygon o_polygon) {
        super(o_polygon);
        this.length = o_polygon.getLength();
        this.sides = o_polygon.getSides();
    }

    /**
     * Constructs a default polygon with a given reference
     *
     * @param reference
     *            the reference
     */
    public TikzPolygon(String reference) {
        super(reference);
        length = Models.DEFAULT.LENGTH;
        sides = Models.DEFAULT.SIDES;
    }

    /**
     * Constructs a polygon with a given length and a given number of sides and
     * reference.
     *
     * @param length
     *            The length of one side
     * @param sides
     *            The number of sides
     * @param reference
     *            the reference
     */
    public TikzPolygon(int length, int sides, String reference) {
        super(reference);
        this.length = length;
        this.sides = sides;
    }

    /**
     * Constructs a tikz polygon by copying an other tikz polygon with a given
     * reference
     *
     * @param o_polygon
     *            The tikz polygon to be copied from
     * @param reference
     *            the reference
     */
    public TikzPolygon(TikzPolygon o_polygon, String reference) {
        super(o_polygon, reference);
        this.length = o_polygon.getLength();
        this.sides = o_polygon.getSides();
    }

    /**
     * Getter for the length of the sides
     *
     * @return The length
     */
    public int getLength() {
        return length;
    }

    /**
     * Setter for the length of the sides
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
     * Getter for the number of sides composing the polygon
     *
     * @return The number of sides composing the polygon
     */
    public int getSides() {
        return sides;
    }

    /**
     * Setter for the number of sides composing the polygon
     *
     * @param sides
     *            The number of sides composing the polygon
     */
    public void setSides(int sides) {
        this.sides = sides;
        setChanged();
        notifyObservers();
    }

    /**
     * Getter for a clone (ie. copy of the current polygon)
     *
     * @return A new polygon that is the copy of the current polygon
     */
    @Override
    public TikzPolygon getClone() {
        return new TikzPolygon(this);
    }

    @Override
    public boolean isPolygon() {
        return true;
    }

    @Override
    public String toString(){
        return TikzFormatter.format(this);
    }
}
