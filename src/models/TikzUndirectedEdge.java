package models;

import java.awt.*;

public class TikzUndirectedEdge extends TikzEdge {
    public TikzUndirectedEdge(TikzNode first, TikzNode second) {
        super(first, second);
    }

    public TikzUndirectedEdge() {
        super();
    }

    public TikzUndirectedEdge(TikzUndirectedEdge o_edge) {
        super(o_edge);
    }

    @Override
    public String toString() {
        Point first = getFirstNode().getPosition(), second = getSecondNode().getPosition();
        String options = String.join(", ", new String[] {}); // TODO
        return String.format("\\draw[%s] (%.0f, %.0f) -- (%.0f, %.0f)", options, first.getX(), first.getY(),
                second.getX(), second.getY());
    }

    @Override
    public TikzUndirectedEdge getClone() {
        return new TikzUndirectedEdge(this);
    }
}
