package gui.editor.views;

import gui.editor.controllers.AbstractButtonPanelController;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractButtonPanel extends JPanel{
    private ButtonGroup buttonGroup;
    private JPanel contentPanel;
    private HashMap<JToggleButton, JPanel> buttons_panels;
    private AbstractButtonPanelController controller;

    public AbstractButtonPanel(){
        this.buttonGroup = new ButtonGroup();
        this.contentPanel = new JPanel();
        this.controller = this.getController();

        this.render();
        this.addListeners();
    }

    private void render() {
        HashMap<String, JPanel> options = this.getOptions();
        for (String name: options.keySet()) {
            JPanel panel = options.get(name);

            JToggleButton button = new JToggleButton(name);
            this.add(button);
            this.buttonGroup.add(button);

            buttons_panels.put(button, panel);
        }

        this.add(contentPanel);
        this.redraw();
    }

    public void redraw(){
        this.contentPanel.removeAll();
        JToggleButton currentTool = this.getCurrentTool();
        if(currentTool != null) {
            this.contentPanel.add(buttons_panels.get(currentTool));
        }
        this.revalidate();
    }

    public JToggleButton getCurrentTool() {
        for (JToggleButton button: this.buttons_panels.keySet()) {
            if(button.isSelected()){
                return button;
            }
        }
        return null;
    }

    abstract AbstractButtonPanelController getController();

    abstract HashMap<String, JPanel> getOptions();

    private void addListeners() {
        for (JToggleButton button: this.buttons_panels.keySet()) {
            button.addActionListener(actionEvent -> controller.buttonClicked(button));
        }
    }
}
