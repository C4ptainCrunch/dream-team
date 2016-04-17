package models;

import java.awt.*;

/**
 * Implementation of the Directed Edge Model (from the MVC architectural pattern)
 * This class represents a directed edge that links two
 * tikz node. The end of the edge is drawn with an arrow
 * pointing to the second tikz node(=destination).
 */
public class TikzDirectedEdge extends TikzEdge {
    /**
     * Constructs a default directed edge
     */
    public TikzDirectedEdge() {
        super();
    }

    /**
     * Constructs a directed edge that is linked to two given nodes
     * @param first The starter node
     * @param second The destination node
     */
    public TikzDirectedEdge(TikzNode first, TikzNode second) {
        super(first, second);
    }

    /**
     * Constructs a directed edge by copying an other directed edge
     * @param o_edge The directed edge to be copied from
     */
    public TikzDirectedEdge(TikzDirectedEdge o_edge) {
        super(o_edge);
    }

    /**
     * Getter for the destination tikz node
     * which is pointed by this directed edge
     * @return the destination tikz node
     */
    public TikzNode destination() {
        return getSecondNode();
    }

    /**
     * Transforms this directed edge into tikz code string
     * @return The tikz code string
     */
    @Override
    public String toString() {
        Point first = getFirstNode().getPosition();
        Point second = getSecondNode().getPosition();
        String options = String.join(", ", new String[] { "->" }); // TODO
        return String.format("\\draw[%s] (%.0f, %.0f) -- (%.0f, %.0f)", options, first.getX(), first.getY(),
                second.getX(), second.getY());
    }

    /**
     *  Getter for a clone (ie. copy of the current directed edge)
     * @return A new directed edge that is the copy of the current directed edge
     */
    @Override
    public TikzDirectedEdge getClone() {
        return new TikzDirectedEdge(this);
    }
}
