package models.tikz;

import static constants.Models.DEFAULT.*;

import java.awt.*;

/**
 * This abstract class defines the common elements of a tikz edge (directed
 * edge, undirected edge) This class represents an edge that links two tikz
 * node.
 */
public abstract class TikzEdge extends TikzComponent {
    private TikzNode firstNode;
    private TikzNode secondNode;
    private Point fromPosition;
    private Point toPosition;

    /**
     * Constructs a default edge
     */
    protected TikzEdge() {
        super();
        firstNode = null;
        secondNode = null;
        fromPosition = new Point(X, Y);
        toPosition = new Point(X + EDGE_X_LENGTH, Y);
    }

    /**
     * Constructs an edge with two given nodes
     *
     * @param first
     *            The first node from which the edge is linked
     * @param second
     *            The second node from which the edge is linked
     */
    protected TikzEdge(TikzNode first, TikzNode second) {
        super();
        firstNode = first;
        secondNode = second;
        fromPosition = first.getPosition();
        toPosition = second.getPosition();
    }

    /**
     * Constructs an edge by copying an other edge
     *
     * @param o_edge
     *            The edge to be copied from
     */
    protected TikzEdge(TikzEdge o_edge) {
        super(o_edge);
        firstNode = o_edge.getFirstNode();
        secondNode = o_edge.getSecondNode();
        fromPosition = new Point(o_edge.getFromPosition());
        toPosition = new Point(o_edge.getToPosition());
    }

    /**
     * Constructs an edge with two given nodes and a given reference
     *
     * @param first
     *            The first node from which the edge is linked
     * @param second
     *            The second node from which the edge is linked
     * @param reference
     *            The reference of the TikzEdge
     */
    public TikzEdge(TikzNode first, TikzNode second, String reference) {
        super(reference);
        this.firstNode = first;
        this.secondNode = second;
        fromPosition = first.getPosition();
        toPosition = second.getPosition();
    }

    /**
     * Constructs an edge by copying an other edge with specified reference
     *
     * @param o_edge
     *            The edge to be copied from
     * @param reference
     *            the reference of the edge
     */
    public TikzEdge(TikzEdge o_edge, String reference) {
        super(o_edge, reference);
        firstNode = o_edge.getFirstNode();
        secondNode = o_edge.getSecondNode();
        fromPosition = o_edge.getFromPosition();
        toPosition = o_edge.getToPosition();
    }

    /**
     * Getter for the mid position of the edge between the two linked nodes
     * using the mid point formula
     *
     * @return The mid point of the edge
     */
    public Point getPosition() {
        Point f_pos = (firstNode == null ? fromPosition : firstNode.getPosition());
        Point to_pos = (secondNode == null ? toPosition : secondNode.getPosition());
        int midX = (int) ((f_pos.getX() + to_pos.getX()) / 2);
        int midY = (int) ((f_pos.getY() + to_pos.getY()) / 2);
        return new Point(midX, midY);
    }

    /**
     * Getter for the position from which the edge starts
     *
     * @return the position from which the edge starts
     */
    public Point getFromPosition() {
        Point pos = (firstNode == null ? fromPosition : firstNode.getPosition());
        return pos;
    }

    /**
     * Setter for the position from which the edge starts
     *
     * @param point
     *            The position from which the edge starts
     */
    public void setFromPosition(Point point) {
        fromPosition = point;
        setChanged();
        notifyObservers();
    }

    /**
     * Getter for the position to which the edge ends
     *
     * @return the position to which the edge ends
     */
    public Point getToPosition() {
        Point pos = (secondNode == null ? toPosition : secondNode.getPosition());
        return pos;
    }

    /**
     * Setter for the position to which the edge ends
     *
     * @param point
     *            the position to whoch the edge ends
     */
    public void setToPosition(Point point) {
        toPosition = point;
        setChanged();
        notifyObservers();
    }

    /**
     * Getter for the node from which the edge starts
     *
     * @return The node from which the edge starts
     */
    public TikzNode getFirstNode() {
        return firstNode;
    }

    /**
     * Setter for the node from which the edge starts
     *
     * @param node
     *            The node from which the edge starts
     */
    public void setFirstNode(TikzNode node) {
        firstNode = node;
        fromPosition = node.getPosition();
        setChanged();
        notifyObservers();
    }

    /**
     * Getter for the node to which the edge ends
     *
     * @return The node to which the edge ends
     */
    public TikzNode getSecondNode() {
        return secondNode;
    }

    /**
     * Setter for the node to which the edge ends
     *
     * @param node
     *            The node to which the edge ends
     */
    public void setSecondNode(TikzNode node) {
        secondNode = node;
        toPosition = node.getPosition();
        setChanged();
        notifyObservers();
    }

    /**
     * Abstract method that needs to be redefined in classes that extends this
     * class. Getter for a clone (ie. copy of the current object)
     *
     * @return A new object that is the copy of the current object
     */
    public abstract TikzEdge getClone();

    @Override
    public boolean isEdge() {
        return true;
    }

    public boolean isDirectedEdge() { return false; }
    public boolean isUndirectedEdge() { return false; }
}
