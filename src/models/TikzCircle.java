package models;

import constants.Models;

import java.awt.*;

public class TikzCircle extends TikzShape {
    private int radius;
    public TikzCircle(){
        super();
        radius = Models.DEFAULT.LENGTH;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
