package models;

import java.awt.*;

public class TikzDirectedEdge extends TikzEdge {
	public TikzDirectedEdge(TikzNode first, TikzNode second) {
		super(first, second);
	}

	public TikzNode destination() {
		return getSecondNode();
	}

	@Override
	public String toString() {
		Point first = getFirstNode().getPosition(), second = getSecondNode().getPosition();
		String options = String.join(", ", new String[] { "->" }); // TODO
		return String.format("\\draw[%s] (%.0f, %.0f) -- (%.0f, %.0f)", options, first.getX(), first.getY(),
				second.getX(), second.getY());
	}
}
