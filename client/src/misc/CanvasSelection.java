package misc;

import constants.GUI;

import java.awt.*;
import java.awt.geom.Point2D;
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

    private Rectangle selection;

    /**
     * Default constructor.
     *
     * @param pos
     *            The position where the selection will be displayed.
     */

    public CanvasSelection(Point2D.Float pos) {
        this.setSize(new Dimension(0, 0));
        this.setBackground(GUI.Selection.BKG_COLOR);
        this.setLocation(new Point((int)pos.x, (int)pos.y));
        selection = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    /**
     * Resize the selection rectangle.
     *
     * @param bottom_right
     *            The position of the bottom right corner of the selection
     *            rectangle.
     */

    public void resize(Point2D.Float bottom_right) {
        int delta_x = (int) bottom_right.getX() - this.getX();
        int delta_y = (int) bottom_right.getY() - this.getY();
        this.setSize(new Dimension(delta_x, delta_y));
    }

    public boolean contains(Point2D.Float point) {
        return selection.contains(point);
    }

    /**
     * Get the selection as an awt.Rectangle object.
     * @return the selection's rectangle
     */

    public Rectangle getSelectionRectangle(){
        return this.selection;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        selection.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
