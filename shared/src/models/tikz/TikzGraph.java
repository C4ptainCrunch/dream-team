package models.tikz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import parser.NodeParser;

/**
 * Implementation of the Graph Model (from the MVC architectural pattern) This
 * class represents all the elements(nodes + edges) composing the graph being
 * used in the current project
 */
public class TikzGraph extends Observable implements Iterable<TikzNode>, Observer {
    private List<TikzEdge> edges = new Vector<>();
    private List<TikzNode> nodes = new Vector<>();

    /**
     * Constructs an empty graph
     */
    public TikzGraph() {
    }

    /**
     * Constructs a graph from a file from a given file path The file contains
     * the tikz code that represents the graph, which can be parsed and
     * transformed into nodes and edges objects
     *
     * @param filePath
     *            The file path
     */
    public TikzGraph(String filePath) throws IOException {
        String stringGraph = new String(Files.readAllBytes(Paths.get(filePath)));
        NodeParser.parseDocument(this).parse(stringGraph);
    }

    /**
     * Copy Constructor.
     *
     * @param o_graph
     */

    public TikzGraph(TikzGraph o_graph) {
        HashMap<TikzNode, TikzNode> origin_to_clone_map = new HashMap<>();
        for (TikzNode node: o_graph.getNodes()){
            origin_to_clone_map.put(node, node.getClone());
            add(origin_to_clone_map.get(node));
        }

        for (TikzEdge edge: o_graph.getEdges()){
            TikzEdge new_edge = edge.getClone();
            new_edge.setFirstNode(origin_to_clone_map.get(edge.getFirstNode()));
            new_edge.setSecondNode(origin_to_clone_map.get(edge.getSecondNode()));
            add(new_edge);
        }
    }

    /**
     * Getter for the number of nodes composing the graph
     *
     * @return The number of nodes
     */
    public int size() {
        return nodes.size();
    }

    /**
     * Getter for an iterator object of the nodes composing the graph
     *
     * @return The iterator of the nodes
     */
    public Iterator<TikzNode> iterator() {
        return nodes.iterator();
    }

    /**
     * Replaces this graph with a given graph by copying its attributes
     *
     * @param other
     *            The tikz graph to be copied from
     */
    public void replace(TikzGraph other) {
        List<TikzNode> oldNodes = this.getNodes();
        oldNodes.forEach(n -> this.remove(n, false));
        this.addAllNodes(other.getNodes(), false);
        this.addAllEdges(other.getEdges(), false);
        setChanged();
        notifyObservers();
    }

    /**
     * Adds a node to this graph and notifies its observers
     *
     * @param node
     *            The node to be added
     *
     */
    public void add(TikzNode node) {
        this.add(node, true);
    }

    /**
     * Add a node to this graph and notifies its observer if notify
     * @param node
     * @param notify
     */
    public void add(TikzNode node, boolean notify) {
        if (!this.nodes.contains(node)) {
            nodes.add(node);
            node.addObserver(this);
            setChanged();
        }
        if(notify) {
            notifyObservers();
        }
    }

    /**
     * Adds an edge to this graph and notifies its observers
     *
     * @param edge
     *            The edge to be added
     */
    public void add(TikzEdge edge) {
        this.add(edge, true);
    }

    /**
     * Adds an edge to this graph and notifies its observers if notify
     * @param edge
     * @param notify
     */
    public void add(TikzEdge edge, boolean notify) {
        this.add(edge.getFirstNode(), notify);
        this.add(edge.getSecondNode(), notify);
        if (!this.edges.contains(edge)) {
            this.edges.add(edge);
            edge.addObserver(this);
            setChanged();
        }
        if(notify) {
            notifyObservers();
        }
    }

    /**
     * Adds a graph to this graph and notifies its observers
     *
     * @param graph
     *            The graph to be added
     */
    public void add(TikzGraph graph) {
        this.addAllNodes(graph.getNodes());
        this.addAllEdges(graph.getEdges());
        notifyObservers();
    }

    /**
     * Adds a collection of edges to this graph and notifies its observers
     *
     * @param edges
     *            The edges to be added
     */
    public void addAllEdges(Collection<TikzEdge> edges) {
        this.addAllEdges(edges, true);
    }

    /**
     * Adds a collection of edges to this graph and notifies its observers if notify
     * @param edges
     * @param notify
     */
    public void addAllEdges(Collection<TikzEdge> edges, boolean notify) {
        edges.forEach(e -> this.add(e, false));
        if(notify) {
            notifyObservers();
        }
    }

    /**
     * Adds a collection of nodes to this graph and notifies its observers
     *
     * @param nodes
     *            The nodes to be added
     */
    public void addAllNodes(Collection<TikzNode> nodes) {
        this.addAllNodes(nodes, true);
    }

    /**
     * Adds a collection of nodes to this graph and notifies its observers if notify
     * @param nodes
     * @param notify
     */
    public void addAllNodes(Collection<TikzNode> nodes, boolean notify) {
        nodes.forEach(n -> this.add(n, false));
        if(notify) {
            notifyObservers();
        }

    }

    /**
     * Getter for the nodes composing the graph
     *
     * @return All the nodes composing the graph
     */
    public List<TikzNode> getNodes() {
        return new ArrayList<>(this.nodes);
    }

    /**
     * Getter for the edges composing the graph
     *
     * @return All the edges composing the graph
     */
    public List<TikzEdge> getEdges() {
        return new ArrayList<>(this.edges);
    }

    public List<TikzComponent> getComponents() {
        List<TikzComponent> components = new ArrayList<>();
        components.addAll(this.nodes);
        components.addAll(this.edges);
        return Collections.unmodifiableList(components);
    }

    /**
     * Getter for the edges linked to a given node composing the graph
     *
     * @param node
     *            The node
     * @return the edges
     */
    public List<TikzEdge> get(TikzNode node) {
        List<TikzEdge> edges = this.edges.stream()
                .filter(edge -> node == edge.getFirstNode() || node == edge.getSecondNode())
                .collect(Collectors.toList());
        return edges;
    }

    /**
     * Transforms this graph into tikz code string
     *
     * @return The tikz code string
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        getNodes().forEach(res::append);
        this.getEdges().forEach(res::append);
        return res.toString();
    }

    public String toLatex(){
        return constants.Models.Graph.latexPrelude + this.toString() + constants.Models.Graph.latexPostlude;
    }

    /**
     * Removes the given edge from this graph and notify its observer
     *
     * @param edge
     *            The edge to be deleted
     */
    public void remove(TikzEdge edge) {
        this.remove(edge, true);
    }

    /**
     * Removes the given edge from this graph and notify its observer if notify
     * @param edge
     * @param notify
     */
    public void remove(TikzEdge edge, boolean notify) {
        this.edges.remove(edge);
        setChanged();
        if(notify) {
            notifyObservers();
        }
        edge.deleteObserver(this);
    }

    /**
     * Removes the given node from this graph. If edges are linked to the node,
     * they are removed too and notify its observer
     *
     * @param node
     *            The node to be removed
     * @return The edges linked to the node to be removed
     */
    public List<TikzEdge> remove(TikzNode node) {
        return this.remove(node, true);
    }

    /**
     * Removes the given node from this graph. If edges are linked to the node,
     * they are removed too and notify its observer if notify
     * @param node
     * @param notify
     * @return
     */
    public List<TikzEdge> remove(TikzNode node, boolean notify) {
        ArrayList<TikzEdge> edges = new ArrayList<>();
        for (int i = 0; i < this.edges.size(); i++) {
            TikzEdge edge = this.edges.get(i);
            if (node == edge.getFirstNode() || node == edge.getSecondNode()) {
                edges.add(edge);
                this.remove(edge, false);
                i--;
            }
        }
        nodes.remove(node);
        setChanged();
        if(notify) {
            notifyObservers();
        }
        node.deleteObserver(this);
        return edges;
    }

    @Override
    public void update(Observable observable, Object o) {
        setChanged();
        notifyObservers();
    }

    /**
     * Apply a translation with x and y as offset to all nodes in the graph
     *
     * @param x
     * @param y
     */
    public void translation(int x, int y) {
        for (TikzNode node : this) {
            node.translate(x, y);
        }
    }

    public TikzGraph getClone() {
        return new TikzGraph(this);
    }

    public Optional<TikzNode> findByRef(String ref) {
        for (TikzNode node: this){
            if (ref.equals(node.getReference())) {return Optional.of(node);}
        }
        return Optional.empty();
    }
}
