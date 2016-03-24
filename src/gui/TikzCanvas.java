
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
        nodeR.setPosition(new Point(200, 100));
        graph.add(nodeR);

        TikzNode nodeR2 = new TikzCircle();
        nodeR2.setLabel("Demo Label2");
        nodeR2.setPosition(new Point(200, 500));
        graph.add(nodeR2);

        TikzEdge myedge = new TikzUndirectedEdge(nodeR, nodeR2);
        graph.add(nodeR, myedge);

        nodeR = new TikzPolygon();
        nodeR.setColor(Color.red);
        nodeR.setLabel("Polypocket");
        nodeR.setPosition(new Point(10, 10));
        graph.add(nodeR);

        nodeR = new TikzPolygon();
        nodeR.setColor(Color.blue);
        nodeR.setLabel("Polypocket2");
        nodeR.setPosition(new Point(20, 20));
        graph.add(nodeR);

        for(TikzNode node : graph){
            Drawer drawer;
            if (node instanceof TikzRectangle) {
                drawer = new RectangleDrawer((TikzRectangle) node);
            }
            else if (node instanceof TikzPolygon) {
                drawer = new PolygonDrawer((TikzPolygon) node);
            }
            else if (node instanceof TikzCircle) {
                drawer = new CircleDrawer((TikzCircle) node);
            }
            else {
                drawer = new UnknownDrawer();
            }
            Vector<Drawable> drawables = drawer.toDrawable();

            for (Drawable drawable : drawables) {
                drawable.translate(node.getPosition());
                drawable.draw((Graphics2D) g);
            }
        }

        for(TikzEdge edge : graph.getEdges()){
            Drawer drawer;
            if (edge instanceof TikzDirectedEdge) {
                drawer = new DirectedEdgeDrawer((TikzDirectedEdge) edge);
            }
            else if (edge instanceof TikzUndirectedEdge) {
                drawer = new UndirectedEdgeDrawer((TikzUndirectedEdge) edge);
            }
            else {
                drawer = new UnknownDrawer();
            }

            Vector<Drawable> drawables = drawer.toDrawable();
            for (Drawable drawable : drawables) {
                drawable.draw((Graphics2D) g);
            }
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
