package parser;

import java.awt.*;
import java.util.Map;
import java.util.Optional;

/**
 * Created by acaccia on 18/04/16.
 */

class DestructuredNode {
    private final Point coordinates;
    private final Map<String, String> options;
    private final String label;
    private final Optional<String> ref;

    public DestructuredNode(Point s, Map<String, String> t, String u) {
        coordinates = s;
        options = t;
        label = u;
        ref = Optional.empty();
    }

    public DestructuredNode(Map<String, String> options, String ref, Point coordinates, String label) {
        this.coordinates = coordinates;
        this.options = options;
        this.label = label;
        this.ref = Optional.of(ref);
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public String getLabel() {
        return label;
    }

    public Optional<String> getRef() {return ref;}

    @Override
    public String toString() {
        return "Coordinates: " + coordinates.toString() + ", Options: " + options.toString() + ", Label: "
                + label;
    }
}