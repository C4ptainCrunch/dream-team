package gui.editor.toolbox.views;


import gui.editor.drag.TikzTransferHandler;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;
import gui.editor.views.canvas.drawers.ComponentDrawer;
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
public class Preview extends JPanel {
    TikzComponent component;
    public Preview(){
        this.setBackground(Color.WHITE);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        enableDrag();
    }

    private void enableDrag(){
        this.setTransferHandler(new TikzTransferHandler());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                Preview view = (Preview) mouseEvent.getSource();
                TransferHandler handler = view.getTransferHandler();
                handler.exportAsDrag(view, mouseEvent, TransferHandler.MOVE);
            }
        });
    }

    public void setComponent(TikzComponent component){
        this.component = component;
    }

    public TikzComponent getComponent() {return component;}

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(component != null) {
            DrawableTikzComponent drawable = Drawer.toDrawable(component);
            drawable.tikz2swing(this);
            drawable.draw((Graphics2D) g);
        }
    }
}
