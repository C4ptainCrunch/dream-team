package models;

import java.util.*;

public class TikzGraph extends Observable implements Iterable<TikzNode> {
    private Vector<TikzEdge> edges;
    private Vector<TikzNode> nodes;

    public TikzGraph(){
        nodes = new Vector<>();
        edges = new Vector<>();
    }

    public int size(){
        return nodes.size();
    }

    public Iterator<TikzNode> iterator(){
        return nodes.iterator();
    }

    public void replace(TikzGraph other){
        this.edges = other.edges;
        this.nodes = other.nodes;
        setChanged();
        notifyObservers();
    }

    /**
     * @param node key to map to empty TIkzEdge vector.
     * @return true if no key is replaced, else false.
     */
    public void add(TikzNode node){
        if(!this.nodes.contains(node)){
            nodes.add(node);
            setChanged();
        }
        notifyObservers();
    }

    /**
     * @param keyNode tikzNode to append a vector of TikzEdge
     * @param values Vector of TikzEdge to append
     * @return true if new mapping, else false
     */
    public void addAll(Collection<TikzEdge> edges){
        for (TikzEdge edge:edges) {
            this.add(edge);
        }
        notifyObservers();
    }

    public void add(TikzEdge edge){
        this.add(edge.getFirstNode());
        this.add(edge.getSecondNode());
        if(!this.edges.contains(edge)){
            this.edges.add(edge);
            setChanged();
        }
        notifyObservers();
    }

    public List<TikzNode> getNodes(){
        return Collections.unmodifiableList(this.nodes);
    }

    public  List<TikzEdge> getEdges(){
        return Collections.unmodifiableList(this.edges);
    }

    public List<TikzEdge> get(TikzNode node){
        List<TikzEdge> edges = new ArrayList<>();
        for (TikzEdge edge:this.edges) {
            if(node == edge.getFirstNode() || node == edge.getSecondNode()){
                edges.add(edge);
            }
        }
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (TikzNode node: getNodes()) {
            res.append(node);
            res.append(";\n");
        }
        res.append("\n");
        for (TikzEdge edge: this.getEdges()) {
            res.append(edge);
            res.append(";\n");
        }
        return res.toString();
    }

}
