package gui.editor.views;

import javax.swing.*;

import gui.drawables.Drawable;
import gui.drawers.*;
import models.*;

import java.awt.*;
import java.util.Collections;
import java.util.Vector;

public class CanvasView extends JPanel{
    private TikzGraph graph;

    public CanvasView(TikzGraph graph){
        this.graph = graph;

        this.render();
        this.setVisible(true);
    }

    public void render(){}

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
}
