package gui.editor.views;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import gui.drawables.Drawable;
import gui.drawables.DrawableShape;
import gui.drawers.*;
import gui.editor.controllers.CanvasController;
import models.*;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Vector;

public class CanvasView extends JPanel{
    private EditorView parentView;
    private TikzGraph graph;
    private CanvasController controller;
    private boolean isFocused;

    public CanvasView(EditorView parentView, TikzGraph graph){
        this.parentView = parentView;
        this.graph = graph;
        this.controller = new CanvasController(this, graph);
        this.isFocused = isFocusOwner();

        this.render();

        this.addListeners();
        this.setVisible(true);
    }

    private void render() {
        setFocusable(true);
//        requestFocusInWindow();
    }

    private void addListeners(){
        this.addMouseListener(new MouseInputAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                if(SwingUtilities.isRightMouseButton(e)){}
                else {
                    controller.mousePressed(e);
                }
            }
        });

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                controller.focusGained();
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                controller.focusLost();
            }
        });
    }

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

        if(!getIsFocused()){
            Drawable drawable = new DrawableShape(
                    new Rectangle(10000, 10000),
                    new BasicStroke(0),
                    Color.white,
                    new Color(0, 0, 0, 64)
            );
            drawable.draw((Graphics2D) g);
        }

    }

    public void setIsFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }

    public boolean getIsFocused() {
        return isFocused;
    }
}
