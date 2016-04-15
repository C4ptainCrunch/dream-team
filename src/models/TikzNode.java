package models;

import java.awt.*;

import constants.Models;

public abstract class TikzNode extends TikzComponent {
    private Point position;

    protected TikzNode() {
        super();
        position = new Point(Models.DEFAULT.X, Models.DEFAULT.Y);
    }

    protected TikzNode(Point position) {
        super();
        this.position = position;
    }

    protected TikzNode(TikzNode o_node) {
        super(o_node);
        this.position = o_node.getPosition();
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void move(int x, int y) {
        setPosition(new Point(x, y));
    }

    public abstract TikzNode getClone();

}
