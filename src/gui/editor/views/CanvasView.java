package gui.editor.views;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import gui.editor.drag.handler.CanvasTransferHandler;
import gui.editor.drag.transfertdata.TransferTikz;
import models.tikz.TikzComponent;
import models.tikz.TikzEdge;
import models.tikz.TikzGraph;
import models.tikz.TikzNode;
import gui.editor.controllers.CanvasController;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;
import gui.editor.views.canvas.drawers.Drawer;

/**
 * Implementation of the View (from the MVC architectural pattern) for the Canvas.
 * The Canvas is the area of the GUI where the graph is painted.
 */
public class CanvasView extends JPanel {
    private final EditorView parentView;
    private final TikzGraph graph;
    private final CanvasController controller;
    private boolean isFocused;
    private PopupMenuView popupMenu;

    /**
     * Constructs a new View for the Canvas,
     * with a given TikzGraph and parentView
     * @param parentView The view which contains this view (ie. EditorView)
     * @param graph The TikzGraph
     */
    public CanvasView(EditorView parentView, TikzGraph graph) {
        this.parentView = parentView;
        this.graph = graph;
        this.controller = new CanvasController(this, graph);
        this.isFocused = isFocusOwner();
        this.popupMenu = new PopupMenuView(controller);

        this.render();
        this.addListeners();
        this.enableDrag();

        this.setVisible(true);
    }

    /**
     * Renders the view by putting it on focus
     */
    private void render() {
        setFocusable(true);
        // requestFocusInWindow();
    }

    /**
     * Adds listeners for mouse events to the view
     */
    private void addListeners() {
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    requestFocusInWindow();
                    CanvasView view = (CanvasView) e.getSource();
                    TikzComponent component = view.getSelectedComponent();
                    if (component != null) {
                        popupMenu.show(view, e.getX(), e.getY());
                        popupMenu.setComponent(component);
                    }
                } else {
                    CanvasView view = (CanvasView) e.getSource();
                    TransferHandler handler = view.getTransferHandler();
                    handler.exportAsDrag(view, e, TransferHandler.MOVE);
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
    }

    /**
     * Paints the components composing the graph
     * @param g Graphics to be drawn
     */
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

    /**
     * Returns true if this view is focused
     * @return true if this view is focused
     */
    public boolean getIsFocused() {
        return isFocused;
    }

    /**
     * Sets whether the view is focused or not
     * @param isFocused The boolean to set whether the view is focused or not
     */
    public void setIsFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }

    public TikzComponent getSelectedComponent(){
        return controller.findComponentByPosition(getMousePosition());
    }

    /**
     * Informs the controller where the component being dragged and dropped has been dropped on the canvas
     * @param transfer_data The selected component being dragged and dropped
     * @param location The location where the component has been dropped
     */

    public void dragEvent(TransferTikz transfer_data, Point location) {
        this.requestFocus();
        controller.mouseDropped(transfer_data, location);
    }

    public void resetTool() {
        parentView.resetTool();
    }

}
