package gui.editor.toolbox;

import gui.editor.views.canvas.drawers.CircleDrawer;
import gui.editor.views.canvas.drawers.EdgeDrawer;
import gui.editor.views.canvas.drawers.UndirectedEdgeDrawer;
import models.TikzTriangle;
import models.TikzUndirectedEdge;

import java.awt.*;

/**
 * Created by jhellinckx on 12/04/16.
 */
public class EdgeSelector extends Selector{
    public EdgeSelector(){
        super();
        this.setComponentNbr(2);
        this.initComponents();
    }

    private void initComponents(){

    }

    private void addUndirectedEdge(){
        TikzUndirectedEdge edge = new TikzUndirectedEdge();
        this.addComponent(new SelectorComponent(new UndirectedEdgeDrawer(edge), edge));
    }

    private void addDirectedEdge(){

    }
}
