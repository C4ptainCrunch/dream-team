package gui.editor.views.canvas.drawers;

import java.awt.*;

import models.tikz.TikzComponent;
import models.tikz.TikzTriangle;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;

/**
 * Created by aurelien on 12/04/16.
 */
public class TriangleDrawer extends ComponentDrawer {

    public TriangleDrawer() {
        // this was left intentionally blank
    }

    @Override
    public DrawableTikzComponent toDrawable(TikzComponent component) {
        TikzTriangle triangle = (TikzTriangle) component;
        DrawableTikzComponent drawableComponent = super.toDrawable(triangle);

        drawableComponent.addShape(getAwtTriangle(triangle));
        drawableComponent.setBackground(triangle.getBackground());
        return drawableComponent;
    }

    private double[] getAngles(TikzTriangle comp) {
        double[] angles = new double[3];
        double a = comp.getSideA();
        double b = comp.getSideB();
        double c = comp.getSideC();
        angles[0] = Math.acos((-Math.pow(a, 2) + Math.pow(b, 2) + Math.pow(c, 2)) / (2 * b * c));
        angles[1] = Math.acos((-Math.pow(b, 2) + Math.pow(a, 2) + Math.pow(c, 2)) / (2 * a * c));
        angles[2] = Math.acos((-Math.pow(c, 2) + Math.pow(a, 2) + Math.pow(b, 2)) / (2 * a * b));
        return angles;
    }

    private Polygon getAwtTriangle(TikzTriangle triangle) {

        int[] x = new int[3];
        int[] y = new int[3];
        int[] sides = triangle.getSides();
        double[] angles = getAngles(triangle);
        x[0] = 0;
        y[0] = (int) (Math.sin(angles[1]) * sides[1]);
        x[1] = sides[0];
        y[1] = (int) (Math.sin(angles[1]) * sides[1]);
        ;
        x[2] = (int) (Math.cos(angles[0]) * sides[2]);
        y[2] = 0;

        return new Polygon(x, y, 3);
    }

}
