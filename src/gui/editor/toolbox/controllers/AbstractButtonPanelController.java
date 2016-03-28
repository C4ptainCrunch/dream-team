package gui.editor.toolbox.controllers;


import gui.editor.toolbox.views.AbstractButtonPanel;

import javax.swing.*;

public abstract class AbstractButtonPanelController {
    protected final AbstractButtonPanel view;

    AbstractButtonPanelController(AbstractButtonPanel view){
        this.view = view;
    }

    public abstract void buttonClicked(JToggleButton button);
}

