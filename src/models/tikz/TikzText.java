package models.tikz;

public class TikzText extends TikzNode {
    public TikzText() {
        super();
    }

    @Override
    public TikzText getClone() {
        return new TikzText();
    }
}
