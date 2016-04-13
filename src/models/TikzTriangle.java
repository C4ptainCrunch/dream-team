package models;

import constants.Models;

public class TikzTriangle extends TikzShape {
    private int sideA;
    private int sideB;
    private int sideC;

    public TikzTriangle(){
        super();
        sideA = Models.DEFAULT.LENGTH;
        sideB = Models.DEFAULT.LENGTH;
        sideC = Models.DEFAULT.LENGTH;
    }

    public TikzTriangle(int[] sides){
        super();
        setSides(sides);
    }

    public TikzTriangle(int sideA, int sideB, int sideC){
        super();
        int[] sides = {sideA, sideB, sideC};
        setSides(sides);
    }

    public TikzTriangle(TikzTriangle o_triangle){
        super(o_triangle);
        setSides(o_triangle.getSides());
    }

    public void setSides(int[] sides) {
        if(sides.length != 3) {
            throw new IllegalArgumentException("The size of the sides array must equals 3.");
        }
        setSideA(sides[0]);
        setSideB(sides[1]);
        setSideC(sides[2]);
    }

    public void setEquilateral(int length){

        setSideA(length);
        setSideB(length);
        setSideC(length);
    }

    public void setSideA(int sideA){
        this.sideA = sideA;
    }

    public void setSideB(int sideB){
        this.sideB = sideB;
    }

    public void setSideC(int sideC){
        this.sideC = sideC;
    }

    public int getSideA(){
        return sideA;
    }

    public int getSideB(){
        return sideB;
    }

    public int getSideC(){
        return sideC;
    }

    public double triangleArea() {
        double s = (sideA + sideB + sideC) / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }

    public double trianglePerimeter() {
        return sideA + sideB + sideC;
    }

    public int[] getSides(){
        int[] sides = {getSideA(), getSideB(), getSideC()};
        return sides;
    }

    @Override
    public TikzTriangle getClone(){
        return new TikzTriangle(this);
    }
}
