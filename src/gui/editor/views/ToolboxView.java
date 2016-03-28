package gui.editor.views;

import gui.editor.controllers.ToolboxController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ToolboxView extends JPanel {
    private EditorView parentView;
    private ButtonGroup buttons;
    private HashMap<JToggleButton, String> buttonsMap;

    private JPanel toolPanel;

    private final String NODE = "node";
    private final String EDGE = "edge";
    private ToolboxController controller;

    public ToolboxView(EditorView parentView) {
        this.parentView = parentView;
        this.controller = new ToolboxController(this);

        this.toolPanel = new JPanel();
        this.buttons = new ButtonGroup();
        this.buttonsMap = new HashMap<>();

        this.render();
        this.addListeners();
    }

    private void addListeners() {
        for (JToggleButton button: this.buttonsMap.keySet()) {
            button.addActionListener(actionEvent -> controller.toolClicked());
        }
    }

    private void render() {
        JToggleButton node = new JToggleButton("node");
        this.buttonsMap.put(node, NODE);
        this.add(node);
        this.buttons.add(node);

        JToggleButton edge = new JToggleButton("edge");
        this.buttonsMap.put(edge, EDGE);
        this.add(edge);
        this.buttons.add(edge);

        this.add(toolPanel);
        this.redraw();
    }

    public void redraw() {
        this.toolPanel.removeAll();
        String currentTool = this.getCurrentTool();
        if(currentTool == NODE){
            this.toolPanel.add(new JLabel("NodePanel"));
        }
        else if(currentTool == EDGE){
            this.toolPanel.add(new JLabel("EdgePanel"));
        }
        else {
            this.toolPanel.add(new JLabel("Select a tool"));
        }
        this.revalidate();

    }

    public String getCurrentTool() {
        for (JToggleButton button: this.buttonsMap.keySet()) {
            if(button.isSelected()){
                return this.buttonsMap.get(button);
            }
        }
        return "NONE";
    }
}
