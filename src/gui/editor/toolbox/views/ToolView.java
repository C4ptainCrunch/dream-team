package gui.editor.toolbox.views;


import gui.editor.toolbox.Selector;

import javax.swing.*;
import java.awt.*;

public class ToolView extends JPanel {

    private static final String NODE_TAB = "Node";
    private static final String EDGE_TAB = "Edge";

    private JTabbedPane tabbedSelector;
    private Selector nodeSelector;
    private Selector edgeSelector;
    private JPanel attributesSelector;
    private JPanel preview;

    public ToolView(){
        initLayout();
        initSelectors();
        addSelectors();
        drawNodeOptions();

    }

    private void initLayout(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void setSelectorDimension(Selector selector){
        selector.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/3));
    }

    private void initSelectors(){
        tabbedSelector = new JTabbedPane();
        nodeSelector = new Selector(3);
        edgeSelector = new Selector(2);
        setSelectorDimension(nodeSelector);
        setSelectorDimension(edgeSelector);
        attributesSelector = new JPanel();
        preview = new JPanel();
        tabbedSelector.addTab(NODE_TAB, nodeSelector);
        tabbedSelector.addTab(EDGE_TAB, edgeSelector);
        tabbedSelector.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
    }

    private void addSelectors(){
        this.add(tabbedSelector);
        tabbedSelector.addTab(NODE_TAB, nodeSelector);
        tabbedSelector.addTab(EDGE_TAB, edgeSelector);
        this.add(attributesSelector);
        this.add(preview);
    }

    private void drawNodeOptions(){

    }


}
