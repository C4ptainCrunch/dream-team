package models.tikz;

/**
 * Implementation of the Text Model (from the MVC architectural pattern) This
 * class represents a tikz void node that is linked to a tikz code
 */
public class TikzVoid extends TikzNode {

    /**
     * Constructs a default void node
     */
    public TikzVoid() {
        super();
    }

    /**
     * Constructs a default void node with a given reference
     *
     * @param reference
     *            the reference
     */
    public TikzVoid(String reference) {
        super(reference);
    }

    /**
     * Transforms this void node into tikz code string
     *
     * @return The tikz code string
     */
    @Override
    public String toString() {
      return "";
    }

    /**
     * Getter for a clone (ie. copy of the current void node)
     *
     * @return A new void node that is the copy of the current void node
     */
    @Override
    public TikzVoid getClone() {
        return new TikzVoid();
    }
}
