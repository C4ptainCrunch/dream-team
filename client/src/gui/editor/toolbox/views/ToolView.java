package gui.editor.toolbox.views;

import java.awt.*;

import javax.swing.*;

import models.tikz.TikzComponent;
import gui.editor.toolbox.model.ToolModel;

/**
 * Implementation of the View (from the MVC architectural pattern) for the Tool.
 * The Tool is the element of the tool box where a tikz component can be selected.
 * The view consists of two tabs to select an edge or a node depending of the tabs.
 */
public class ToolView extends JPanel {

    private static final int TOOL_SIZE = 3;

    private static final String NODE_TAB = "Node";
    private static final String EDGE_TAB = "Edge";

    private JTabbedPane tabbedSelector;
    private SelectorView nodeSelectorView;
    private SelectorView edgeSelectorView;
    private AttributesChooserView attributesChooserView;
    private PreviewView preview;
    private ToolModel model;

    /**
     * Constructs a new view for the Tool
     */
    public ToolView() {
        initLayout();
        initToolModel();
        initSelectors();
        setPanelsDimension();
        addSelectors();
    }

    /**
     * Initializes the layout
     */
    private void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Setter for the dimension of a panel
     * @param pan The panel to be set
     */
    private void setPanelDimension(JPanel pan) {
        pan.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / TOOL_SIZE));
    }

    /**
     * Setter for the different panels composing this view
     */
    private void setPanelsDimension() {
        setPanelDimension(nodeSelectorView);
        setPanelDimension(edgeSelectorView);
        setPanelDimension(attributesChooserView);
        setPanelDimension(preview);
    }

    /**
     * Initializes the selectors
     */
    private void initSelectors() {
        tabbedSelector = new JTabbedPane();
        nodeSelectorView = new NodeSelectorView(model);
        edgeSelectorView = new EdgeSelectorView(model);
        attributesChooserView = new AttributesChooserView(model);
        preview = new PreviewView(model);
        tabbedSelector.addTab(NODE_TAB, nodeSelectorView);
        tabbedSelector.addTab(EDGE_TAB, edgeSelectorView);
        tabbedSelector.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
    }

    /**
     * Adds the selectors to this view
     */
    private void addSelectors() {
        this.add(tabbedSelector);
        tabbedSelector.addTab(NODE_TAB, nodeSelectorView);
        tabbedSelector.addTab(EDGE_TAB, edgeSelectorView);
        this.add(attributesChooserView);
        this.add(preview);
    }

    /**
     * Initializes the model
     */
    private void initToolModel() {
        model = new ToolModel();
    }

    /**
     * Getter for the tikz component that has been selected
     * @return The selected tikz component
     */
    public TikzComponent getTikzComponent() {
        return model.getComponent();
    }

}
