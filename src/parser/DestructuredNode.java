package parser;

import java.awt.*;

/**
 * Created by acaccia on 18/04/16.
 */

class DestructuredNode {
    private final Point coordinates;
    private final java.util.Map<String, String> options;
    private final String label;

    public DestructuredNode(Point s, java.util.Map<String, String> t, String u) {
        coordinates = s;
        options = t;
        label = u;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public java.util.Map<String, String> getOptions() {
        return options;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "Coordinates: " + coordinates.toString() + ", Options: " + options.toString() + ", Label: "
                + label;
    }
}