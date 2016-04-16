package gui.editor.toolbox.views;

import models.tikz.TikzDirectedEdge;
import models.tikz.TikzUndirectedEdge;
import gui.editor.toolbox.SelectorComponent;
import gui.editor.toolbox.model.ToolModel;

public class EdgeSelectorView extends SelectorView {
    public EdgeSelectorView(ToolModel model) {
        super(model);
        this.setComponentNbr(2);
        this.initComponents();
    }

    private void initComponents() {
        addUndirectedEdge();
        addDirectedEdge();
    }

    private void addUndirectedEdge() {
        this.addComponent(new SelectorComponent(new TikzUndirectedEdge(), this));
    }

    private void addDirectedEdge() {
        this.addComponent(new SelectorComponent(new TikzDirectedEdge(), this));
    }
}
