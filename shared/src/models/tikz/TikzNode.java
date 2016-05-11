package models.tikz;

import java.awt.geom.Point2D;

import constants.Models;

/**
 * This abstract class defines the common elements of a tikz node (shape, text,
 * void, ..)
 */
public abstract class TikzNode extends TikzComponent {
    private Point2D.Float position;

    /**
     * Constructs a default tikz node
     */
    protected TikzNode() {
        super();
        position = new Point2D.Float(Models.DEFAULT.X, Models.DEFAULT.Y);
    }

    /**
     * Constructs a tikz node with a given position
     *
     * @param position
     *            The position
     */
    protected TikzNode(Point2D.Float position) {
        super();
        this.position = position;
    }

    /**
     * Constructs a tikz node by copying an other tikz node
     *
     * @param o_node
     *            The tikz node to be copied from
     */
    protected TikzNode(TikzNode o_node) {
        super(o_node);
        float x = o_node.getPosition().x;
        float y = o_node.getPosition().y;
        this.position = new Point2D.Float(x, y);
    }

    /**
     * Constructs a default tikz node with a given reference
     *
     * @param reference
     *            the reference for this node
     */
    protected TikzNode(String reference) {
        super(reference);
        position = new Point2D.Float(Models.DEFAULT.X, Models.DEFAULT.Y);
    }

    /**
     * Constructs a default tikz node with a given reference
     *
     * @param reference
     *            the reverence for the component
     */
    public TikzNode(Point2D.Float position, String reference) {
        super(reference);
        this.position = position;
    }

    /**
     * Constructs a tikz node by copying an other tikz component with a given
     * reference
     *
     * @param o_node
     *            The tikz compmonent to be copied from
     * @param reference
     *            the reference for the component
     */
    public TikzNode(TikzNode o_node, String reference) {
        super(o_node, reference);
        this.position = o_node.getPosition();
    }

    /**
     * Getter for the position of this tikz node
     *
     * @return the position
     */
    public Point2D.Float getPosition() {
        return position;
    }

    /**
     * Setter for the position of this tikz node
     *
     * @param position
     *            The position
     */
    public void setPosition(Point2D.Float position) {
        this.position = position;
        setChanged();
        notifyObservers();
    }

    /**
     * Changes the position of this tikz node with the given x and y coordinates
     *
     * @param x
     *            x coordinate
     * @param y
     *            y coordinate
     */
    public void move(int x, int y) {
        setPosition(new Point2D.Float(x, y));
        setChanged();
        notifyObservers();
    }

    /**
     * Abstract method that needs to be redefined in classes that extends this
     * class. Getter for a clone (ie. copy of the current object)
     *
     * @return A new object that is the copy of the current object
     */
    public abstract TikzNode getClone();

    /**
     * Move this tikz node by adding x and y to its coordinates.
     *
     * @param dx
     * @param dy
     */
    public void translate(float dx, float dy) {
        float x = this.position.x;
        float y = this.position.y;
        this.position.setLocation(x + dx, y + dy);
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean isNode() {
        return true;
    }

    public boolean isShape() {
        return false;
    }

    public boolean isVoid() { return false; }
    public boolean isText() { return false; }

    public TikzGraph toGraph() {
        TikzGraph graph = new TikzGraph();
        graph.add(this);
        return graph;
    }
}
