package gui.drawers;

import models.TikzEdge;
import models.TikzDirectedEdge;

public class DirectedEdgeDrawer extends EdgeDrawer {
    public DirectedEdgeDrawer(TikzDirectedEdge component) {
        super(component);
    }
    public TikzDirectedEdge getComponent(){
        return (TikzDirectedEdge) super.getComponent();
    }
}
