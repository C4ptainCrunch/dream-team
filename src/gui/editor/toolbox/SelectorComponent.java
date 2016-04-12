package gui.editor.toolbox;

import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawers.Drawer;

import javax.swing.*;
import java.awt.*;

public class SelectorComponent extends JPanel {

    private Drawer drawer;
    private int shape_size;

    public SelectorComponent(Drawer d){
        drawer = d;
    }

    public void setShapeSize(int size){
        shape_size = size;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for (Drawable drawable: drawer.toDrawable()){
            drawable.translate(new Point(0,0), this);
            drawable.draw((Graphics2D)(g));
        }
    }
}
