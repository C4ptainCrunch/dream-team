package models;

import constants.Models;

public class TikzRectangle extends TikzShape {
    private int width;
    private int length;

    public TikzRectangle() {
        super();
        width = Models.DEFAULT.LENGTH;
        length = Models.DEFAULT.LENGTH;
    }

    public TikzRectangle(int width, int length) {
        super();
        setWidth(width);
        setLength(length);
    }

    public TikzRectangle(TikzRectangle o_rectangle) {
        super(o_rectangle);
        width = o_rectangle.getWidth();
        length = o_rectangle.getLength();
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
        String options = String.join(", ", new String[] { "rectangle" }); // TODO:
                                                                            // do
                                                                            // this
        if (!options.contains("draw")) {
            options += ", draw";
        }
        return String.format("\\node[%s](%s) at (%.0f,%.0f){%s}", options, "", getPosition().getX(),
                getPosition().getY(), getLabel());
    }

    @Override
    public TikzRectangle getClone() {
        return new TikzRectangle(this);
    }
}
