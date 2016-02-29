package models;


public class TikzDirectedEdge extends TikzEdge{
    public TikzDirectedEdge(TikzNode first, TikzNode second){
        super(first, second);
    }

    public TikzNode destination(){
        return getSecondNode();
    }
}

