package models;

import constants.Models;

public class TikzPolygon extends TikzShape {
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

    @Override
    public String toString() {
        String options = String.join(", ", new String[]{"regular polygon"}); //TODO: do this
        return String.format("\\node[%s](%s) at (%.0f,%.0f){%s}", options, "", getPosition().getX(), getPosition().getY(), getLabel());
    }
}
