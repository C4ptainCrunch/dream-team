
package gui;

import gui.drawables.Drawable;
import gui.drawers.Drawer;
import gui.drawers.RectangleDrawer;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class TikzCanvas extends JPanel implements Observer {

    private TikzGraph graph = new TikzGraph();
//    private CanvasControler controler;

//    public TikzCanvas(CanvasControler controler){
//        this.controler = controler;
//    }


    public void drawGraph(Graphics g){
        TikzGraph graph = new TikzGraph();
        TikzNode node = new TikzRectangle(100, 100);
        node.setLabel("Demo Label");
        node.setPosition(new Point(100, 100));
        graph.add(node);

        Drawer drawer = new RectangleDrawer((TikzRectangle) node);
        Vector<Drawable> drawables = drawer.toDrawable();

        for (Drawable drawable : drawables) {
            drawable.translate(node.getPosition());
            drawable.draw((Graphics2D) g);
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawGraph(g);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
