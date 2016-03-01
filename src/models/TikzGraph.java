package models;

import java.util.*;

public class TikzGraph extends AbstractCollection<TikzNode> {
    private Map<TikzNode, Vector<TikzEdge>> graph;

    public TikzGraph(){
        graph = new HashMap<TikzNode, Vector<TikzEdge>>();
    }

    @Override
    public int size(){
        return graph.size();
    }

    @Override
    public Iterator<TikzNode> iterator(){
        return graph.keySet().iterator();
    }

    /**
     * @param node key to map to empty TIkzEdge vector.
     * @return true if no key is replaced, else false.
     */
    @Override
    public boolean add(TikzNode node){
        if(graph.put(node, new Vector<TikzEdge>()) == null)
            return true;
        return false;
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
            return true;
        }
        else{
            edges.addAll(values);
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
            return graph.get(key).remove(pos);
        }
    }

    public boolean remove(TikzNode key, TikzEdge value){
        if(!graph.containsKey(key))
            return false;
        else{
            return graph.get(key).remove(value);
        }
    }

}
