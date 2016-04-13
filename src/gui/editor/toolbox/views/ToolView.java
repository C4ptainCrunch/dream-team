package gui.editor.toolbox.views;

import gui.editor.toolbox.controllers.PreviewController;
import gui.editor.toolbox.model.ToolModel;
import gui.editor.views.canvas.drawers.ComponentDrawer;
import models.TikzComponent;

import javax.swing.*;
import java.awt.*;

public class ToolView extends JPanel {

    private static final int TOOL_SIZE = 3;

    private static final String NODE_TAB = "Node";
    private static final String EDGE_TAB = "Edge";

    private JTabbedPane tabbedSelector;
    private SelectorView nodeSelectorView;
    private SelectorView edgeSelectorView;
    private AttributesChooserView attributesChooserView;
    private Preview preview;
    private ToolModel model;

    public ToolView(){
        initLayout();
        initToolModel();
        initSelectors();
        setPanelsDimension();
        addSelectors();
        addObservers();
    }

    private void initLayout(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void setPanelDimension(JPanel pan){
        pan.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/TOOL_SIZE));
    }

    private void setPanelsDimension(){
        setPanelDimension(nodeSelectorView);
        setPanelDimension(edgeSelectorView);
        setPanelDimension(attributesChooserView);
        setPanelDimension(preview);
    }

    private void initSelectors(){
        tabbedSelector = new JTabbedPane();
        nodeSelectorView = new NodeSelectorView(model);
        edgeSelectorView = new EdgeSelectorView(model);
        attributesChooserView = new AttributesChooserView(model);
        preview = new Preview();
        tabbedSelector.addTab(NODE_TAB, nodeSelectorView);
        tabbedSelector.addTab(EDGE_TAB, edgeSelectorView);
        tabbedSelector.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
    }

    private void addSelectors(){
        this.add(tabbedSelector);
        tabbedSelector.addTab(NODE_TAB, nodeSelectorView);
        tabbedSelector.addTab(EDGE_TAB, edgeSelectorView);
        this.add(attributesChooserView);
        this.add(preview);
    }

    private void initToolModel(){
        model = new ToolModel();
    }

    private void addObservers(){
        model.addObserver(new PreviewController(preview));
    }

    @Override
    public void componentSelected(TikzComponent component){
        model.setComponent(component);
    }

    public TikzComponent getTikzComponent() {
        return preview.getComponent();
    }

}
