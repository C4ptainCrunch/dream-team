package gui.editor.toolbox.views;

import gui.editor.toolbox.controllers.AbstractButtonPanelController;

import javax.swing.*;
import java.util.HashMap;

public class TextPanel extends AbstractButtonPanel {
    TextPanel(String text){
        this.add(new JLabel(text));
    }

    @Override
    AbstractButtonPanel getDefaultPanel() {
        return null;
    }

    @Override
    AbstractButtonPanelController getController() {
        return null;
    }
}
