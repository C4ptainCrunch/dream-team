package parser;

import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Optional;

/**
 * This class contains the elements representing a tikz node and is configured
 * while parsing the tikz text
 */
class DestructuredNode {
    private final Point2D.Float coordinates;
    private final Map<String, String> options;
    private final String label;
    private final Optional<String> ref;

    /**
     * Constructs a new Destructured node with given coordinates, options and
     * label
     *
     * @param s
     *            The coordinates
     * @param t
     *            The options
     * @param u
     *            The label
     */
    public DestructuredNode(Point2D.Float s, Map<String, String> t, String u) {
        coordinates = s;
        options = t;
        label = u;
        ref = Optional.empty();
    }

    /**
     * Constructs a new Destructured node
     *
     * @param options
     *            the options
     * @param ref
     *            the reference
     * @param coordinates
     *            the coordianates
     * @param label
     *            the label
     */
    public DestructuredNode(Map<String, String> options, String ref, Point2D.Float coordinates, String label) {
        this.coordinates = coordinates;
        this.options = options;
        this.label = label;
        this.ref = Optional.of(ref);
    }

    /**
     * Getter for the coordinates of the destructured node
     *
     * @return the Point2D.Float object representing the coordinates
     */
    public Point2D.Float getCoordinates() {
        return coordinates;
    }

    /**
     * Getter for the options defining the destructured node
     *
     * @return the array of options
     */
    public Map<String, String> getOptions() {
        return options;
    }

    /**
     * Getter for the label of the destructured node
     *
     * @return The label of the destructured node
     */
    public String getLabel() {
        return label;
    }

    public Optional<String> getRef() {
        return ref;
    }

    /**
     * Transforms this destructured node into a string representation
     *
     * @return The string representation
     */
    @Override
    public String toString() {
        return "Coordinates: " + coordinates.toString() + ", Options: " + options.toString() + ", Label: " + label;
    }
}
