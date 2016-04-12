package gui.editor.toolbox.views;

import gui.editor.toolbox.AttributesChooser;
import gui.editor.toolbox.EdgeSelector;
import gui.editor.toolbox.NodeSelector;
import gui.editor.toolbox.Selector;

import javax.swing.*;
import java.awt.*;

public class ToolView extends JPanel {

    private static final int TOOL_SIZE = 3;

    private static final String NODE_TAB = "Node";
    private static final String EDGE_TAB = "Edge";

    private JTabbedPane tabbedSelector;
    private Selector nodeSelector;
    private Selector edgeSelector;
    private AttributesChooser attributesChooser;
    private Preview preview;

    public ToolView(){
        initLayout();
        initSelectors();
        setPanelsDimension();
        addSelectors();
        drawNodeOptions();

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
        setPanelDimension(attributesChooser);
        setPanelDimension(preview);
    }

    private void initSelectors(){
        tabbedSelector = new JTabbedPane();
        nodeSelector = new NodeSelector();
        edgeSelector = new EdgeSelector();
        attributesChooser = new AttributesChooser();
        preview = new Preview();
        tabbedSelector.addTab(NODE_TAB, nodeSelector);
        tabbedSelector.addTab(EDGE_TAB, edgeSelector);
        tabbedSelector.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
    }

    private void addSelectors(){
        this.add(tabbedSelector);
        tabbedSelector.addTab(NODE_TAB, nodeSelector);
        tabbedSelector.addTab(EDGE_TAB, edgeSelector);
        this.add(attributesChooser);
        this.add(preview);
    }

    private void drawNodeOptions(){

    }


}
