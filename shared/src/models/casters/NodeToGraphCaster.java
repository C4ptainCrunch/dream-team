package models.casters;

import models.tikz.TikzGraph;
import models.tikz.TikzNode;

/**
 * Static class casting a TikzNode to a TikzGraph.
 */
public final class NodeToGraphCaster {

    public static TikzGraph cast(TikzNode node) {
        TikzGraph graph = new TikzGraph();
        if(node != null){
            graph.add(node);
        }
        return graph;
    }
}
