package models;

import constants.Models;

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

    @Override
    public String toString() {
        String options = String.join(", ", new String[]{"circle"}); //TODO: do this
        return String.format("\\node[%s](%s) at (%.0f,%.0f){%s}", options, "", getPosition().getX(), getPosition().getY(), getLabel());
    }
}
