package models;

import java.awt.*;

import constants.Models;

public abstract class TikzShape extends TikzNode {
    private Color outlineColor;
    private Color backgroundColor;
    private int outlineWidth;

    protected TikzShape(){
        super();
        outlineColor = Models.DEFAULT.COLOR;
        outlineWidth = Models.DEFAULT.WIDTH;
        backgroundColor = Models.DEFAULT.BACKGROUND_COLOR;
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

    public Color getBackground() {
        return backgroundColor;
    }
}
