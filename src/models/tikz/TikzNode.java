package models.tikz;

import java.awt.*;

import constants.Models;

/**
 * This abstract class defines the common elements of a tikz node (shape, text, void, ..)
 */
public abstract class TikzNode extends TikzComponent {
    private Point position;

    /**
     * Constructs a default tikz node
     */
    protected TikzNode() {
        super();
        position = new Point(Models.DEFAULT.X, Models.DEFAULT.Y);
    }

    /**
     * Constructs a tikz node with a given position
     * @param position The position
     */
    protected TikzNode(Point position) {
        super();
        this.position = position;
    }

    /**
     * Constructs a tikz node by copying an other tikz node
     * @param o_node The tikz node to be copied from
     */
    protected TikzNode(TikzNode o_node) {
        super(o_node);
        this.position = o_node.getPosition();
    }

    /**
     * Getter for the position of this tikz node
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Setter for the position of this tikz node
     * @param position The position
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Changes the position of this tikz node with
     * the given x and y coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    public void move(int x, int y) {
        setPosition(new Point(x, y));
    }

    /**
     * Abstract method that needs to be redefined in classes
     * that extends this class.
     * Getter for a clone (ie. copy of the current object)
     * @return A new object that is the copy of the current object
     */
    public abstract TikzNode getClone();

}
