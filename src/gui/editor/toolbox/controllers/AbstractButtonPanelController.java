package gui.editor.toolbox.controllers;


import javax.swing.*;

import gui.editor.toolbox.views.AbstractButtonPanel;

public abstract class AbstractButtonPanelController {
    protected final AbstractButtonPanel view;

    AbstractButtonPanelController(AbstractButtonPanel view){
        this.view = view;
    }

    public abstract void buttonClicked(JToggleButton button);
}

