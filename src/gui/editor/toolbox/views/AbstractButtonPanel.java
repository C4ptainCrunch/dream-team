package gui.editor.toolbox.views;

import java.util.HashMap;

import javax.swing.*;

import gui.editor.toolbox.controllers.AbstractButtonPanelController;

public abstract class AbstractButtonPanel extends JPanel{
    private ButtonGroup buttonGroup;
    private JPanel contentPanel;
    private HashMap<JToggleButton, JPanel> buttons_panels;
    private AbstractButtonPanelController controller;

    public AbstractButtonPanel(){
        this.buttonGroup = new ButtonGroup();
        this.contentPanel = new JPanel();
        this.controller = this.getController();
        this.buttons_panels = new HashMap<>();

        this.render();
        this.addListeners();
    }

    private void render() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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
            System.out.println("Showing " + currentTool.getText() + " panel");
        }
        else {
            this.contentPanel.add(this.getDelfaultPanel());
        }
        this.revalidate();
    }

    abstract JPanel getDelfaultPanel();

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
