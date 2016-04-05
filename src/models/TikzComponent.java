package models;
import java.awt.Color;
import java.util.Observable;

import constants.Models;

public abstract class TikzComponent extends Observable implements Comparable<TikzComponent>{
    private Color color;
    private String label;
    private static int creationNumber = 0;
    private final int order;

    protected TikzComponent(){
        this.color = Models.DEFAULT.COLOR;
        this.label = Models.DEFAULT.LABEL;
        this.order = creationNumber++;
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

    @Override
    public int compareTo(TikzComponent other){
        return Integer.compare(this.order,other.order);
    }
}
