package gui.editor.controllers;


import gui.editor.views.AbstractButtonPanel;

import javax.swing.*;

public abstract class AbstractButtonPanelController {
    private final AbstractButtonPanel view;

    AbstractButtonPanelController(AbstractButtonPanel view){
        this.view = view;
    }

    public abstract void buttonClicked(JToggleButton button);
}

