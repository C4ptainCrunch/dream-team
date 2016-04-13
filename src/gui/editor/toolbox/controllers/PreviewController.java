package gui.editor.toolbox.controllers;

import gui.editor.toolbox.views.Preview;
import gui.editor.views.canvas.drawers.ComponentDrawer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by aurelien on 12/04/16.
 */
public class PreviewController implements Observer {

    private Preview view;

    public PreviewController(Preview v){
        view = v;
    }

    @Override
    public void update(Observable o, Object obj){
        view.repaint();
    }
}
