package gui.editor.toolbox;

import gui.editor.views.canvas.drawers.CircleDrawer;
import gui.editor.views.canvas.drawers.DirectedEdgeDrawer;
import gui.editor.views.canvas.drawers.EdgeDrawer;
import gui.editor.views.canvas.drawers.UndirectedEdgeDrawer;
import models.TikzDirectedEdge;
import models.TikzTriangle;
import models.TikzUndirectedEdge;

import java.awt.*;

public class EdgeSelector extends Selector{
    public EdgeSelector(SelectorListener lis){
        super(lis);
        this.setComponentNbr(2);
        this.initComponents();
    }

    private void initComponents(){
        addUndirectedEdge();
        addDirectedEdge();
    }

    private void addUndirectedEdge(){
        TikzUndirectedEdge edge = new TikzUndirectedEdge();
        this.addComponent(new SelectorComponent(new UndirectedEdgeDrawer(edge), edge, this));
    }

    private void addDirectedEdge(){
        TikzDirectedEdge edge = new TikzDirectedEdge();
        this.addComponent(new SelectorComponent(new DirectedEdgeDrawer(edge), edge, this));
    }
}
