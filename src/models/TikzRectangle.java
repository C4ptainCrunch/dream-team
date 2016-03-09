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

    @Override
    public String toString() {
        String options = String.join(", ", new String[]{"rectangle"}); //TODO: do this
        return String.format("\\node[%s](%s) at (%.0f,%.0f){%s}", options, "", getPosition().getX(), getPosition().getY(), getLabel());
    }
}
