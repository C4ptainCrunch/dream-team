package models.tikz;

import java.awt.*;

public class TikzDirectedEdge extends TikzEdge {
    public TikzDirectedEdge(TikzNode first, TikzNode second) {
        super(first, second);
    }

    public TikzDirectedEdge() {
        super();
    }

    public TikzDirectedEdge(TikzDirectedEdge o_edge) {
        super(o_edge);
    }

    public TikzNode destination() {
        return getSecondNode();
    }

    @Override
    public String toString() {
        Point first = getFirstNode().getPosition();
        Point second = getSecondNode().getPosition();
        String options = String.join(", ", new String[] { "->" }); // TODO
        return String.format("\\draw[%s] (%.0f, %.0f) -- (%.0f, %.0f)", options, first.getX(), first.getY(),
                second.getX(), second.getY());
    }

    @Override
    public TikzDirectedEdge getClone() {
        return new TikzDirectedEdge(this);
    }
}
