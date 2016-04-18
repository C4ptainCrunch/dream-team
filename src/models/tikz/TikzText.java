package models.tikz;

/**
 * Implementation of the Text Model (from the MVC architectural pattern)
 * This class represents a tikz text node that is linked to a tikz code
 */
public class TikzText extends TikzNode {
    /**
     * Constructs a default text node
     */
    public TikzText() {
        super();
    }

    /**
     * Getter for a clone (ie. copy of the current text node)
     * @return A new text node that is the copy of the current text node
     */
    @Override
    public TikzText getClone() {
        return new TikzText();
    }
}
