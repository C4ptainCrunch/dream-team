package models.tikz;

/**
 * Implementation of the Text Model (from the MVC architectural pattern)
 * This class represents a tikz void node that is linked to a tikz code
 */
public class TikzVoid extends TikzNode {

    /**
     * Constructs a default void node
     */
    public TikzVoid() {
        super();
    }

    /**
     * Transforms this void node into tikz code string
     * @return The tikz code string
     */
    @Override
    public String toString() {
        String options = String.join(", ", new String[] {}); // TODO: do this
        return String.format("\\node[%s](%s) at (%.0f,%.0f){%s}", options, "", getPosition().getX(),
                getPosition().getY(), getLabel());
    }

    /**
     *  Getter for a clone (ie. copy of the current void node)
     * @return A new void node that is the copy of the current void node
     */
    @Override
    public TikzVoid getClone() {
        return new TikzVoid();
    }
}
