package models.casters;

import models.tikz.TikzGraph;
import models.tikz.TikzNode;

/**
 * Created by aurelien on 19/04/16.
 */
public final class NodeToGraphCaster {

    public static TikzGraph cast(TikzNode node){
        TikzGraph graph = new TikzGraph();
        graph.add(node);
        return graph;
    }
}
