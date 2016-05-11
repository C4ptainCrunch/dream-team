package models.tikz;

import parser.TikzFormatter;

/**
 * Implementation of the Directed Edge Model (from the MVC architectural
 * pattern) This class represents a directed edge that links two tikz node. The
 * end of the edge is drawn with an arrow pointing to the second tikz
 * node(=destination).
 */
public class TikzDirectedEdge extends TikzEdge {
    /**
     * Constructs a default directed edge
     */
    public TikzDirectedEdge() {
        super();
    }

    /**
     * Constructs a directedEdge with two given nodes and a given reference
     *
     * @param first
     *            The first node from which the edge is linked
     * @param second
     *            The second node from which the edge is linked
     * @param reference
     *            The reference of the TikzEdge
     */
    public TikzDirectedEdge(TikzNode first, TikzNode second, String reference) {
        super(first, second, reference);
    }

    /**
     * Constructs a directedEdge by copying an other edge with specified
     * reference
     *
     * @param o_edge
     *            The edge to be copied from
     * @param reference
     *            the reference of the edge
     */
    public TikzDirectedEdge(TikzEdge o_edge, String reference) {
        super(o_edge, reference);
    }

    /**
     * Constructs a directed edge that is linked to two given nodes
     *
     * @param first
     *            The starter node
     * @param second
     *            The destination node
     */
    public TikzDirectedEdge(TikzNode first, TikzNode second) {
        super(first, second);
    }

    /**
     * Constructs a directed edge by copying an other directed edge
     *
     * @param o_edge
     *            The directed edge to be copied from
     */
    public TikzDirectedEdge(TikzDirectedEdge o_edge) {
        super(o_edge);
    }

    /**
     * Getter for the destination tikz node which is pointed by this directed
     * edge
     *
     * @return the destination tikz node
     */
    public TikzNode destination() {
        return getSecondNode();
    }


    /**
     * Getter for a clone (ie. copy of the current directed edge)
     *
     * @return A new directed edge that is the copy of the current directed edge
     */
    @Override
    public TikzDirectedEdge getClone() {
        return new TikzDirectedEdge(this);
    }

    @Override
    public boolean isDirectedEdge() {
        return true;
    }

    @Override
    public String toString(){
        return TikzFormatter.format(this);
    }
}
