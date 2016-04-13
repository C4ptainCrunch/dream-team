package models;

import java.awt.Point;

import constants.Models;

public abstract class TikzNode extends TikzComponent {
    private Point position;

    protected TikzNode(){
        super();
        position = new Point(Models.DEFAULT.X, Models.DEFAULT.Y);
    }

    protected TikzNode(Point position){
        super();
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void move(int x, int y){
        setPosition(new Point(x,y));
    }

}
