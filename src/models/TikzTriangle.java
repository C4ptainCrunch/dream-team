package models;

import constants.Models;

public class TikzTriangle extends TikzShape {
    private int sideA;
    private int sideB;
    private int sideC;

    public TikzTriangle() {
        super();
        sideA = Models.DEFAULT.LENGTH;
        sideB = Models.DEFAULT.LENGTH;
        sideC = Models.DEFAULT.LENGTH;
    }

    public TikzTriangle(int... sides) {
        super();
        setSides(sides);
    }

    public TikzTriangle(int sideA, int sideB, int sideC) {
        super();
        int[] sides = { sideA, sideB, sideC };
        setSides(sides);
    }

    public TikzTriangle(TikzTriangle o_triangle) {
        super(o_triangle);
        setSides(o_triangle.getSides());
    }

    public void setEquilateral(int length) {

        setSideA(length);
        setSideB(length);
        setSideC(length);
        setChanged();
        notifyObservers();
    }

    public int getSideA() {
        return sideA;
    }

    public void setSideA(int sideA) {
        this.sideA = sideA;
        setChanged();
        notifyObservers();
    }

    public int getSideB() {
        return sideB;
    }

    public void setSideB(int sideB) {
        this.sideB = sideB;
        setChanged();
        notifyObservers();
    }

    public int getSideC() {
        return sideC;
    }

    public void setSideC(int sideC) {
        this.sideC = sideC;
        setChanged();
        notifyObservers();
    }

    public double triangleArea() {
        double s = (sideA + sideB + sideC) / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }

    public double trianglePerimeter() {
        return sideA + sideB + sideC;
    }

    public int[] getSides() {
        return new int[] { getSideA(), getSideB(), getSideC() };
    }

    public void setSides(int... sides) {
        if (sides.length != 3) {
            throw new IllegalArgumentException("The size of the sides array must equals 3.");
        }
        setSideA(sides[0]);
        setSideB(sides[1]);
        setSideC(sides[2]);
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        String options = String.join(", ", new String[] { "regular polygon" }); // TODO:
                                                                                // do
                                                                                // this
        return String.format("\\node[%s](%s) at (%.0f,%.0f){%s}", options, "", getPosition().getX(),
                getPosition().getY(), getLabel());
    }

    @Override
    public TikzTriangle getClone() {
        return new TikzTriangle(this);
    }
}
