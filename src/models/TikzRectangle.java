package models;

import constants.Models;

import java.awt.*;

public class TikzRectangle extends TikzShape {
    private int width;
    private int length;

    public TikzRectangle(){
        super();
        width = Models.DEFAULT.LENGTH;
        length = Models.DEFAULT.LENGTH;
    }

    public TikzRectangle(int width, int length){
        super();
        setWidth(width);
        setLength(length);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
