package gui.editor.views.canvas.drawers;

import models.TikzUndirectedEdge;

public class UndirectedEdgeDrawer extends EdgeDrawer {
    public UndirectedEdgeDrawer(TikzUndirectedEdge component) {
        super(component);
    }
    public TikzUndirectedEdge getComponent(){
        return (TikzUndirectedEdge) super.getComponent();
    }
}
