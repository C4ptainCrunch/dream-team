package gui.editor.toolbox.views;

import gui.editor.toolbox.EdgeSelector;
import gui.editor.toolbox.NodeSelector;
import gui.editor.toolbox.Selector;
import gui.editor.toolbox.controllers.PreviewController;
import gui.editor.toolbox.model.ToolModel;
import gui.editor.views.canvas.drawers.ComponentDrawer;
import models.TikzComponent;

import javax.swing.*;
import java.awt.*;

public class ToolView extends JPanel implements Selector.SelectorListener {

    private static final int TOOL_SIZE = 3;

    private static final String NODE_TAB = "Node";
    private static final String EDGE_TAB = "Edge";

    private JTabbedPane tabbedSelector;
    private Selector nodeSelector;
    private Selector edgeSelector;
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
        setPanelDimension(nodeSelector);
        setPanelDimension(edgeSelector);
        setPanelDimension(attributesChooserView);
        setPanelDimension(preview);
    }

    private void initSelectors(){
        tabbedSelector = new JTabbedPane();
        nodeSelector = new NodeSelector(this);
        edgeSelector = new EdgeSelector(this);
        attributesChooserView = new AttributesChooserView(model);
        preview = new Preview();
        tabbedSelector.addTab(NODE_TAB, nodeSelector);
        tabbedSelector.addTab(EDGE_TAB, edgeSelector);
        tabbedSelector.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
    }

    private void addSelectors(){
        this.add(tabbedSelector);
        tabbedSelector.addTab(NODE_TAB, nodeSelector);
        tabbedSelector.addTab(EDGE_TAB, edgeSelector);
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

    public TikzComponent getTikzComponent(){
        return preview.getComponent();
    }

}
