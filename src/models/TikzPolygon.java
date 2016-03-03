package models;

import constants.Models;

public class TikzPolygon extends TikzNode {
    private int length;
    private int sides;

    public TikzPolygon(){
        super();
        length = Models.DEFAULT.LENGTH;
        sides = Models.DEFAULT.SIDES;
    }

    public TikzPolygon(int length, int sides){
        super();
        this.length = length;
        this.sides = sides;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSides() {
        return sides;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }
}
