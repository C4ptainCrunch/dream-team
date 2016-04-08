package models;

import parser.NodeParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TikzGraph extends Observable implements Iterable<TikzNode> {
    private Map<TikzNode, Vector<TikzEdge>> graph;

    public TikzGraph(){
        graph = new TreeMap<TikzNode, Vector<TikzEdge>>();
    }

    public TikzGraph(String filePath) {
        graph = new TreeMap<TikzNode, Vector<TikzEdge>>();
        try{
            String stringGraph = new String(Files.readAllBytes(Paths.get(filePath)));
            NodeParser.parseDocument(this).parse(stringGraph);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public int size(){
        return graph.size();
    }

    public Iterator<TikzNode> iterator(){
        return graph.keySet().iterator();
    }

    public void replace(TikzGraph other){
        this.graph = other.graph;
        setChanged();
        notifyObservers();
    }

    /**
     * @param node key to map to empty TIkzEdge vector.
     * @return true if no key is replaced, else false.
     */
    public boolean add(TikzNode node){
        Boolean is_new_key = graph.put(node, new Vector<TikzEdge>()) == null;
        setChanged();
        notifyObservers();
        return is_new_key;
    }

    /**
     * @param keyNode tikzNode to append a vector of TikzEdge
     * @param values Vector of TikzEdge to append
     * @return true if new mapping, else false
     */
    public boolean addAll(TikzNode keyNode, Collection<TikzEdge> values){
        Vector<TikzEdge> edges = graph.get(keyNode);
        if(edges == null) {
            graph.put(keyNode, new Vector<TikzEdge>(values));
            setChanged();
            notifyObservers();
            return true;
        }
        else{
            edges.addAll(values);
            setChanged();
            notifyObservers();
            return false;
        }
    }

    public boolean add(TikzNode key, TikzEdge value){
        Vector<TikzEdge> toAdd = new Vector<TikzEdge>(1);
        toAdd.add(value);
        return addAll(key, toAdd);
    }

    public Map<TikzNode, Vector<TikzEdge>> getAll(){
        return Collections.unmodifiableMap(graph);
    }

    public Set<TikzNode> getNodes(){
        return Collections.unmodifiableSet(graph.keySet());
    }

    public  List<TikzEdge> getEdges(){
        ArrayList<TikzEdge> edges = new ArrayList<TikzEdge>();
        for (TikzNode node:this) {
            edges.addAll(get(node));
        }
        return Collections.unmodifiableList(edges);
    }

    public List<TikzEdge> get(TikzNode key){
        if(graph.containsKey(key))
            return Collections.unmodifiableList(graph.get(key));
        else
            return null;
    }

    public Vector<TikzEdge> remove(TikzNode key){
        return graph.remove(key);
    }

    public TikzEdge remove(TikzNode key, int pos){
        if(!graph.containsKey(key))
            return null;
        else{
            setChanged();
            notifyObservers();
            return graph.get(key).remove(pos);
        }
    }

    public boolean remove(TikzNode key, TikzEdge value){
        if(!graph.containsKey(key))
            return false;
        else{
            setChanged();
            notifyObservers();
            return graph.get(key).remove(value);
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (TikzNode node: getNodes()) {
            res.append(node);
            res.append(";\n");
        }
        res.append("\n");
        for (TikzNode node: getNodes())
            for (TikzEdge edge: this.get(node))
                if (node == edge.getFirstNode()) {
                    res.append(edge);
                    res.append(";\n");
                }
        return res.toString();
    }

}
