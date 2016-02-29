package models;

public abstract class TikzEdge extends TikzComponent{
    private TikzNode firstNode;
    private TikzNode secondNode;

    protected TikzEdge(TikzNode first, TikzNode second){
        super();
        firstNode = first;
        secondNode = second;
    }

    public TikzNode getFirstNode(){
        return firstNode;
    }

    public TikzNode getSecondNode(){
        return secondNode;
    }
}
