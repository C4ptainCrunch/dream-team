package gui.editor.toolbox.views;


import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawers.Drawer;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * Created by aurelien on 12/04/16.
 */
public class Preview extends JPanel {

    private Drawer drawer;


    public Preview(){
        this.setBackground(Color.WHITE);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    }

    public void setDrawer(Drawer d){
        drawer = d;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if (drawer != null) {
            for (Drawable drawable : drawer.toDrawable()) {
                drawable.translate(new Point(0, 0), this);
                drawable.draw((Graphics2D) (g));
            }
        }
    }
}
