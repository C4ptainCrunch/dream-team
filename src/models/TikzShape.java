package models;

import constants.Models;

import java.awt.*;

public abstract class TikzShape extends TikzNode {
    private Color outlineColor;
    private int outlineWidth;

    protected TikzShape(){
        super();
        outlineColor = Models.DEFAULT.COLOR;
        outlineWidth = Models.DEFAULT.WIDTH;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public int getOutlineWidth() {
        return outlineWidth;
    }

    public void setOutlineWidth(int outlineWidth) {
        this.outlineWidth = outlineWidth;
    }
}
