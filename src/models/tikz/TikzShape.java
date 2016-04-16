package models.tikz;

import java.awt.*;

import constants.Models;

public abstract class TikzShape extends TikzNode {
    private Color outlineColor;
    private Color backgroundColor;
    private int outlineWidth;

    protected TikzShape() {
        super();
        outlineColor = Models.DEFAULT.COLOR;
        outlineWidth = Models.DEFAULT.WIDTH;
        backgroundColor = Models.DEFAULT.BACKGROUND_COLOR;
    }

    protected TikzShape(TikzShape o_shape) {
        super(o_shape);
        outlineColor = o_shape.getOutlineColor();
        outlineWidth = o_shape.getOutlineWidth();
        backgroundColor = o_shape.getBackground();
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

    public abstract TikzShape getClone();
}
