package gui.editor.views;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.basic.BasicButtonListener;

import gui.drawables.Drawable;
import gui.drawers.*;
import gui.editor.controllers.CanvasController;
import models.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class CanvasView extends JPanel implements Observer{
    private TikzGraph graph;
    private CanvasController controller;

    public CanvasView(TikzGraph graph){
        this.graph = graph;
        this.graph.addObserver(this);
        this.controller = new CanvasController(this, graph);

        this.render();
        this.addListeners();
        this.setVisible(true);
    }

    private void addListeners(){
        this.addMouseListener(new MouseInputAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                TikzNode nodeR2 = new TikzCircle();
                nodeR2.setLabel("Demo Label " + graph.getAll().size());
                nodeR2.setPosition(e.getPoint());
                graph.add(nodeR2);
            }
        });
    }

    private void render(){}

    public void paintComponent(Graphics g){
        super.paintComponent(g);

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

            Collections.reverse(drawables);
            for (Drawable drawable : drawables) {
                drawable.translate(node.getPosition());
                drawable.draw((Graphics2D) g);
            }
        }

    }

    public void update(Observable o, Object arg){
        repaint();
    }
}
