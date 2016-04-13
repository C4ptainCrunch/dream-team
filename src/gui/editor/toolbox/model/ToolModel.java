package gui.editor.toolbox.model;

import java.awt.*;
import java.util.Observable;
import gui.editor.views.canvas.drawers.ComponentDrawer;
import models.TikzComponent;

/**
 * Created by aurelien on 13/04/16.
 */
public class ToolModel extends Observable {

    private TikzComponent component;

    private void alertObservers(){
        this.setChanged();
        notifyObservers(component);
    }

    public void setComponentLabel(String label){
        component.setLabel(label);
        alertObservers();
    }

    public void setComponentColor(Color color){
        component.setColor(color);
        alertObservers();
    }

    public TikzComponent getComponent(){
        return component;
    }

    public void setComponent(TikzComponent component) {
        this.component = component;
        alertObservers();
    }

}
