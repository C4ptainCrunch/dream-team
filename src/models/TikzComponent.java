package models;
import java.awt.Color;
import java.util.Observable;

import constants.Models;

public abstract class TikzComponent extends Observable {
    private Color color;
    private String label;

    protected TikzComponent(){
        this.color = Models.DEFAULT.COLOR;
        this.label = Models.DEFAULT.LABEL;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
