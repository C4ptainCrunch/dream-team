package gui.editor.toolbox;

import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawers.Drawer;
import models.TikzComponent;
import models.TikzNode;

import javax.swing.*;
import java.awt.*;

public class SelectorComponent extends JPanel {

    private Drawer drawer;
    private int shape_size;
    private TikzComponent component;

    public SelectorComponent(Drawer d, TikzComponent comp){
        drawer = d;
        component = comp;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for (Drawable drawable: drawer.toDrawable()){
            if(component instanceof TikzNode){
                drawable.translate(new Point(((TikzNode)component).getPosition()), this);
            }
            drawable.draw((Graphics2D)(g));
        }
    }
}
