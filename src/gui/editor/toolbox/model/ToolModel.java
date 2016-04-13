package gui.editor.toolbox.model;

import java.awt.*;
import java.util.Observable;
import gui.editor.views.canvas.drawers.ComponentDrawer;
import models.TikzComponent;

/**
 * Created by aurelien on 13/04/16.
 */
public class ToolModel extends Observable {

    private ComponentDrawer drawer;
    private TikzComponent component;

    public void setDrawer(ComponentDrawer draw){
        this.drawer = draw;
        component = draw.getComponent();
        alertObservers();
    }

    private void alertObservers(){
        this.setChanged();
        notifyObservers(drawer);
    }

    public void setComponentLabel(String label){
        component.setLabel(label);
        alertObservers();
    }

    public void setComponentColor(Color color){
        component.setColor(color);
        alertObservers();
    }

    public ComponentDrawer getDrawer(){
        return drawer;
    }

    public TikzComponent getComponent(){
        return component;
    }

}
