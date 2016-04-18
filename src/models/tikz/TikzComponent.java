package models.tikz;

import java.awt.*;
import java.util.Observable;

import constants.Models;

public abstract class TikzComponent extends Observable {
    private Color color;
    private String label;
    private int stroke;

    protected TikzComponent() {
        this.color = Models.DEFAULT.COLOR;
        this.label = Models.DEFAULT.LABEL;
        this.stroke = Models.DEFAULT.STROKE;
    }

    protected TikzComponent(TikzComponent o_comp) {
        this.color = o_comp.getColor();
        this.label = o_comp.getLabel();
        this.stroke = o_comp.getStroke();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getStroke() {
        return this.stroke;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
    }

    public abstract TikzComponent getClone();
}
