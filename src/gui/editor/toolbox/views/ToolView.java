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
        initLayout();
        initSelectors();
        addSelectors();
        drawNodeOptions();

    }

    private void initLayout(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
