package gui.editor.toolbox.views;


import gui.editor.drag.TikzTransferHandler;
import gui.editor.toolbox.controllers.PreviewController;
import gui.editor.toolbox.model.ToolModel;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;
import gui.editor.views.canvas.drawers.Drawer;
import models.TikzComponent;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by aurelien on 12/04/16.
 */
public class PreviewView extends JPanel {
    TikzComponent component;
    PreviewController controller;

    public PreviewView(ToolModel model){
        this.setBackground(Color.WHITE);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        this.controller = new PreviewController(this, model);
        enableDrag();
    }

    private void enableDrag(){
        this.setTransferHandler(new TikzTransferHandler());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                PreviewView view = (PreviewView) mouseEvent.getSource();
                TransferHandler handler = view.getTransferHandler();
                handler.exportAsDrag(view, mouseEvent, TransferHandler.MOVE);
            }
        });
    }

    public void setComponent(TikzComponent component){
        this.component = component;
    }

    public TikzComponent getComponent(){
        return this.controller.getComponent();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(component != null) {
            DrawableTikzComponent drawable = Drawer.toDrawable(component);
            drawable.tikz2swing(this);
            drawable.draw((Graphics2D) g);
        }
    }
}
