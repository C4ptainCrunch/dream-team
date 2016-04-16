package models;

import static constants.Models.DEFAULT.*;

import java.awt.*;

public abstract class TikzEdge extends TikzComponent {
    private TikzNode firstNode;
    private TikzNode secondNode;
    private Point fromPosition;
    private Point toPosition;

    protected TikzEdge(TikzNode first, TikzNode second) {
        super();
        firstNode = first;
        secondNode = second;
        fromPosition = first.getPosition();
        toPosition = second.getPosition();
    }

    protected TikzEdge(TikzEdge o_edge) {
        super(o_edge);
        firstNode = o_edge.getFirstNode();
        secondNode = o_edge.getSecondNode();
        fromPosition = o_edge.getFromPosition();
        toPosition = o_edge.getToPosition();
    }

    protected TikzEdge() {
        super();
        firstNode = null;
        secondNode = null;
        fromPosition = new Point(X, Y);
        toPosition = new Point(X + EDGE_X_LENGTH, Y);
    }

    // Use the mid point formula to get mid Edge position.
    public Point getPosition() {
        int midX = (int) ((fromPosition.getX() + toPosition.getX()) / 2);
        int midY = (int) ((fromPosition.getY() + toPosition.getY()) / 2);
        return new Point(midX, midY);
    }

    public Point getFromPosition() {
        return fromPosition;
    }

    public void setFromPosition(Point point) {
        fromPosition = point;
        setChanged();
        notifyObservers();
    }

    public Point getToPosition() {
        return toPosition;
    }

    public void setToPosition(Point point) {
        toPosition = point;
        setChanged();
        notifyObservers();
    }

    public TikzNode getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(TikzNode node) {
        firstNode = node;
        fromPosition = node.getPosition();
        setChanged();
        notifyObservers();
    }

    public TikzNode getSecondNode() {
        return secondNode;
    }

    public void setSecondNode(TikzNode node) {
        secondNode = node;
        toPosition = node.getPosition();
        setChanged();
        notifyObservers();
    }

    public abstract TikzEdge getClone();
}
