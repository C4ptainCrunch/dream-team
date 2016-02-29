package models;

import constants.Models;

public class TikzTriangle extends TikzShape {
    private int[] sides;

    public TikzTriangle(){
        super();
        sides = new int[]{Models.DEFAULT.LENGTH, Models.DEFAULT.LENGTH, Models.DEFAULT.LENGTH};
    }

    public int[] getSides() {
        return sides;
    }

    public void setSides(int[] sides) {
        if(sides == null) {
            throw new NullPointerException();
        }

        if(sides.length != 3) {
            throw new IllegalArgumentException("The size of the sides array must equals 3.");
        }
        this.sides = sides;
    }
}
