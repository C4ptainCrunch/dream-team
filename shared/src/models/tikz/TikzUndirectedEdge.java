package models.tikz;

import java.awt.*;

/**
 * Implementation of the Undirected Edge Model (from the MVC architectural
 * pattern) This class represents an undirected edge that links two tikz node.
 */
public class TikzUndirectedEdge extends TikzEdge {

    /**
     * Constructs a default undirected edge
     */
    public TikzUndirectedEdge() {
        super();
    }

    /**
     * Constructs an undirected edge that is linked to two given nodes
     *
     * @param first
     *            The starter node
     * @param second
     *            The destination node
     */
    public TikzUndirectedEdge(TikzNode first, TikzNode second) {
        super(first, second);
    }

    /**
     * Constructs an undirected edge by copying an other undirected edge
     *
     * @param o_edge
     *            The undirected edge to be copied from
     */
    public TikzUndirectedEdge(TikzUndirectedEdge o_edge) {
        super(o_edge);
    }

    /**
     * Constructs an UndirectedEdge with two given nodes and a given reference
     *
     * @param first
     *            The first node from which the edge is linked
     * @param second
     *            The second node from which the edge is linked
     * @param reference
     *            The reference of the TikzEdge
     */
    public TikzUndirectedEdge(TikzNode first, TikzNode second, String reference) {
        super(first, second, reference);
    }

    /**
     * Constructs an UndirectedEdge by copying an other edge with specified
     * reference
     *
     * @param o_edge
     *            The edge to be copied from
     * @param reference
     *            the reference of the edge
     */
    public TikzUndirectedEdge(TikzEdge o_edge, String reference) {
        super(o_edge, reference);
    }

    /**
     * Transforms this undirected edge into tikz code string
     *
     * @return The tikz code string
     */
    @Override
    public String toString() {
        Point first = getFirstNode().getPosition();
        Point second = getSecondNode().getPosition();
        String options = String.join(", ", new String[] {}); // TODO
        return String.format("\\draw[%s] (%.0f, %.0f) -- (%.0f, %.0f)", options, first.getX(), first.getY(), second.getX(), second.getY());
    }

    /**
     * Getter for a clone (ie. copy of the current undirected edge)
     *
     * @return A new undirected edge that is the copy of the current undirected
     *         edge
     */
    @Override
    public TikzUndirectedEdge getClone() {
        return new TikzUndirectedEdge(this);
    }
}
