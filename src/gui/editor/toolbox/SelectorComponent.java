package gui.editor.toolbox;

import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawers.Drawer;
import models.TikzNode;

import javax.swing.*;
import java.awt.*;

public class SelectorComponent extends JPanel {

    private Drawer drawer;
    private int shape_size;
    private TikzNode component;

    public SelectorComponent(Drawer d, TikzNode comp){
        drawer = d;
        component = comp;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for (Drawable drawable: drawer.toDrawable()){
            drawable.translate(new Point(component.getPosition()), this);
            drawable.draw((Graphics2D)(g));
        }
    }
}
