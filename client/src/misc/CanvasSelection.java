package misc;

import constants.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * A CanvasSelection is a JPanel that represents the selection of components on
 * the CanvasView.
 *
 * It will be displayed as a rectangle.
 */

public class CanvasSelection extends JPanel {

    private static final int INCREMENT = 5;

    private Rectangle selection;

    /**
     * Default constructor.
     *
     * @param pos
     *            The position where the selection will be displayed.
     */

    public CanvasSelection(Point pos) {
        this.setSize(new Dimension(0, 0));
        this.setBackground(GUI.Selection.BKG_COLOR);
        this.setLocation(pos);
        this.selection = null;
    }

    /**
     * Resize the selection rectangle.
     *
     * @param bottom_right
     *            The position of the bottom right corner of the selection
     *            rectangle.
     */

    public void resize(Point bottom_right) {
        int delta_x = (int) bottom_right.getX() - this.getX();
        int delta_y = (int) bottom_right.getY() - this.getY();
        this.setSize(new Dimension(delta_x, delta_y));
    }

    public boolean contains(Point point) {
        return selection.contains(point);
    }

    /**
     * Get all the Points composing the selection rectangle.
     *
     * Actually, because a Point is basically a pixel, this function returns
     * significantly less points because close Points are likely to appear
     * similar to the user. As so, the number of Points returned is equals to
     * the total amount of Points in the selection rectangle divided by a
     * constant&sup2;.
     *
     * @return A list of Point objects composing the selection rectangle.
     */

    public List<Point> getShapePoints() {
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < selection.getWidth(); i += INCREMENT) {
            for (int j = 0; j < selection.getHeight(); j += INCREMENT) {
                points.add(new Point((int) (i + selection.getX()), (int) (j + selection.getY())));
            }
        }
        return points;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        selection = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
