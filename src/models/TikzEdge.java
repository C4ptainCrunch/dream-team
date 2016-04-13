package models;

import java.awt.Point;
import static constants.Models.DEFAULT.*;

public abstract class TikzEdge extends TikzComponent{
    private TikzNode firstNode;
    private TikzNode secondNode;
    private Point fromPosition;
    private Point toPosition;

    protected TikzEdge(TikzNode first, TikzNode second){
        super();
        firstNode = first;
        secondNode = second;
        fromPosition = first.getPosition();
        toPosition = second.getPosition();
    }

    protected TikzEdge(){
        super();
        firstNode = null;
        secondNode = null;
        fromPosition = new Point(X, Y);
        toPosition = new Point(X + EDGE_X_LENGTH, Y);
    }
    // Use the mid point formula to get mid Edge position.
    public Point getPosition() {
        int midX = (int)((fromPosition.getX() + toPosition.getX())/2);
        int midY = (int)((fromPosition.getY() + toPosition.getY())/2);
        return new Point(midX, midY);
    }
    public void setFromPosition(Point point){ fromPosition = point; }
    public void setToPosition(Point point){ toPosition = point; }
    public Point getFromPosition() { return fromPosition; }
    public Point getToPosition() { return toPosition; }
    public void setFirstNode(TikzNode node) { firstNode = node; fromPosition = node.getPosition(); }
    public void setSecondNode(TikzNode node) { secondNode = node; toPosition = node.getPosition(); }
    public TikzNode getFirstNode(){
        return firstNode;
    }
    public TikzNode getSecondNode(){
        return secondNode;
    }
}
