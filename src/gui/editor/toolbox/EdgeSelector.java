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
        this.addComponent(new SelectorComponent(new TikzUndirectedEdge(), this));
    }

    private void addDirectedEdge(){
        this.addComponent(new SelectorComponent(new TikzDirectedEdge(), this));
    }
}
