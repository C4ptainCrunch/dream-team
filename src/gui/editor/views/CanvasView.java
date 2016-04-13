package gui.editor.views;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import gui.editor.drag.TikzTransferHandler;
import models.*;
import gui.editor.controllers.CanvasController;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;
import gui.editor.views.canvas.drawers.*;

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
        this.setTransferHandler(new TikzTransferHandler());

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
                    controller.mousePressed(e, parentView.getSelectedTool());
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
            DrawableTikzComponent drawableComponent =  Drawer.toDrawable(edge);
            drawableComponent.draw((Graphics2D) g);
        }

        for(TikzNode node : graph){
            DrawableTikzComponent drawableComponent =  Drawer.toDrawable(node);
            drawableComponent.draw((Graphics2D) g);
        }

        if(!getIsFocused()){
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Stroke old_stroke = g2d.getStroke();
            Color old_color = g2d.getColor();
            g2d.setStroke(new BasicStroke(0));
            g2d.setColor(new Color(0, 0, 0, 64));
            Shape s = new Rectangle(10000, 10000);
            g2d.fill(s);
            g2d.setColor(Color.white);
            g2d.draw(s);
            g2d.setColor(old_color);
            g2d.setStroke(old_stroke);
        }

    }

    public void setIsFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }

    public boolean getIsFocused() {
        return isFocused;
    }

    public void dragEvent(TikzComponent component, Point location){
        controller.focusGained();
        controller.mouseDropped(component, location);
    }
}
