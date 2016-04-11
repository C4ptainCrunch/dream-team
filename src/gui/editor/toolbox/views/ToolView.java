package gui.editor.toolbox.views;


import javax.swing.*;

public class ToolView extends JPanel {

    private static final String NODE_TAB = "Node";
    private static final String EDGE_TAB = "Edge";

    private JTabbedPane tabbedSelector;
    private JPanel nodeSelector;
    private JPanel edgeSelector;
    private JPanel attributesSelector;
    private JPanel preview;

    public ToolView(){
        initSelectors();

    }

    private void initSelectors(){
        tabbedSelector = new JTabbedPane();
        nodeSelector = new JPanel();
        edgeSelector = new JPanel();
        attributesSelector = new JPanel();
        preview = new JPanel();
        tabbedSelector.addTab(NODE_TAB, nodeSelector);
        tabbedSelector.addTab(EDGE_TAB, edgeSelector);
        JLabel txt = new JLabel("Bonjour");
        nodeSelector.add(txt);
        JLabel txt2 = new JLabel("Hello");
        edgeSelector.add(txt2);
        this.add(tabbedSelector);
        this.add(nodeSelector);
        this.add(edgeSelector);
        this.add(attributesSelector);
        this.add(preview);
    }


}
