
package gui;

import gui.drawables.Drawable;
import gui.drawers.Drawer;
import gui.drawers.*;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.Collections;

public class TikzCanvas extends JPanel implements Observer {

    private TikzGraph graph = new TikzGraph();
//    private CanvasControler controler;

//    public TikzCanvas(CanvasControler controler){
//        this.controler = controler;
//    }


    public void drawGraph(Graphics g){
        TikzGraph graph = new TikzGraph();
        TikzNode nodeR = new TikzRectangle(100, 100);
        nodeR.setLabel("Demo Label");
        nodeR.setPosition(new Point(250, 200));
        graph.add(nodeR);

        TikzNode nodeR2 = new TikzCircle();
        nodeR2.setLabel("Demo Label2");
        nodeR2.setPosition(new Point(300, 600));
        graph.add(nodeR2);

        TikzEdge myedge = new TikzDirectedEdge(nodeR, nodeR2);
        graph.add(nodeR, myedge);

        nodeR = new TikzPolygon();
        nodeR.setColor(Color.red);
        nodeR.setLabel("Polypocket");
        nodeR.setPosition(new Point(110, 110));
        graph.add(nodeR);

        myedge = new TikzUndirectedEdge(nodeR2, nodeR);
        graph.add(nodeR, myedge);




    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawGraph(g);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
