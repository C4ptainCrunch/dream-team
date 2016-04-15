package gui.editor.views;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import gui.editor.drag.handler.CanvasTransferHandler;
import models.TikzComponent;
import models.TikzEdge;
import models.TikzGraph;
import models.TikzNode;
import gui.editor.controllers.CanvasController;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;
import gui.editor.views.canvas.drawers.Drawer;

public class CanvasView extends JPanel {
    private EditorView parentView;
    private TikzGraph graph;
    private CanvasController controller;
    private boolean isFocused;

    public CanvasView(EditorView parentView, TikzGraph graph) {
        this.parentView = parentView;
        this.graph = graph;
        this.controller = new CanvasController(this, graph);
        this.isFocused = isFocusOwner();

        this.render();

        this.addListeners();
        this.enableDrag();
        this.setVisible(true);
    }

    private void render() {
        setFocusable(true);
        // requestFocusInWindow();
    }

    private void addListeners() {
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (SwingUtilities.isRightMouseButton(e)) {
                } else {
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

    private void enableDrag() {
        this.setTransferHandler(new CanvasTransferHandler());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                CanvasView view = (CanvasView) mouseEvent.getSource();
                TransferHandler handler = view.getTransferHandler();
                handler.exportAsDrag(view, mouseEvent, TransferHandler.MOVE);
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (TikzEdge edge : graph.getEdges()) {
            DrawableTikzComponent drawableComponent = Drawer.toDrawable(edge);
            drawableComponent.draw((Graphics2D) g);
        }

        for (TikzNode node : graph) {
            DrawableTikzComponent drawableComponent = Drawer.toDrawable(node);
            drawableComponent.translate(node.getPosition());
            drawableComponent.center();
            controller.addDrawableComponent(drawableComponent);
            drawableComponent.draw((Graphics2D) g);
        }

        if (!getIsFocused()) {
            Graphics2D g2d = (Graphics2D) g;
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

    public boolean getIsFocused() {
        return isFocused;
    }

    public void setIsFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }

    public TikzComponent getSelectedComponent(){
        return controller.findComponentByPosition(getMousePosition());
    }

    public void dragEvent(TikzComponent component, Point location) {
        this.requestFocus();
        controller.mouseDropped(component, location);
    }
}
