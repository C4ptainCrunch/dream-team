package gui.editor.controllers.canvascontroller;

import models.TikzComponent;

/**
 * Created by aurelien on 14/04/16.
 */

// This Class allows the CanvasController to keep a state of one action (here, a mouse clicked on a component).

public class CanvasControllerState {

    private TikzComponent component;
    private TikzComponent related_component;

    public CanvasControllerState(){
        component = null;
        related_component = null;
    }

    public CanvasControllerState(TikzComponent comp, TikzComponent pos){
        component = comp;
        related_component = pos;
    }

    public boolean equalsTo(CanvasControllerState o_state) {
        return ((o_state.getComponent() == this.component) && (o_state.getRelatedComponent() == this.getRelatedComponent()));
    }

    public boolean initialized(){
        return ((component != null) && (related_component != null));
    }

    public boolean componentEqualsTo(TikzComponent comp){
        return (comp == this.component);
    }

    public TikzComponent getComponent(){
        return component;
    }

    public TikzComponent getRelatedComponent() {
        return related_component;
    }

    public void setComponent(TikzComponent component) {
        this.component = component;
    }

    public void setRelatedComponent(TikzComponent related_component) {
        this.related_component = related_component;
    }

    public void reset(){
        this.component = null;
        this.related_component = null;
    }
}
