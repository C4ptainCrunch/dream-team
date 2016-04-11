package gui.editor.toolbox.views;

import java.util.HashMap;

import javax.swing.*;

import gui.editor.toolbox.controllers.AbstractButtonPanelController;

public abstract class AbstractButtonPanel extends JPanel{
    private ButtonGroup buttonGroup;
    private JPanel contentPanel;
    private HashMap<JToggleButton, AbstractButtonPanel> buttons_panels;
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

        HashMap<String, AbstractButtonPanel> options = this.getOptions();
        for (String name: options.keySet()) {
            AbstractButtonPanel panel = options.get(name);
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
            if(this.getDefaultPanel() != null) {
                this.contentPanel.add(this.getDefaultPanel());
            }
        }
        this.revalidate();
    }

    abstract AbstractButtonPanel getDefaultPanel();

    public JToggleButton getCurrentTool() {
        for (JToggleButton button: this.buttons_panels.keySet()) {
            if(button.isSelected()){
                return button;
            }
        }
        return null;
    }

    abstract AbstractButtonPanelController getController();

    protected HashMap<String, AbstractButtonPanel> getOptions(){
        return new HashMap<>();
    }

    public HashMap<String, Object> getProperties(){
        HashMap<String, Object> ret = new HashMap<>();
        if(this.getCurrentPanel() != null) {
            ret.putAll(this.getCurrentPanel().getProperties());
        }
        return ret;
    }

    private void addListeners() {
        if(this.controller != null) {
            for (JToggleButton button : this.buttons_panels.keySet()) {
                button.addActionListener(actionEvent -> controller.buttonClicked(button));
            }
        }
    }

    public AbstractButtonPanel getCurrentPanel() {
        return this.buttons_panels.get(this.getCurrentTool());
    }
}
