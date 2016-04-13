package gui.editor.toolbox.views;


import gui.editor.drag.TikzTransferHandler;
import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawers.ComponentDrawer;
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
<<<<<<< HEAD
||||||| merged common ancestors

    private Drawer drawer;


=======

    private ComponentDrawer drawer;


>>>>>>> 892a9086c0bf0311b0e053e3b289df4b7021c8d5
    public Preview(){
        this.setBackground(Color.WHITE);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        enableDrag();
    }

<<<<<<< HEAD
||||||| merged common ancestors
    public void setDrawer(Drawer d){
        drawer = d;
    }

=======
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

    public void setDrawer(ComponentDrawer d){
        drawer = d;
    }

>>>>>>> 892a9086c0bf0311b0e053e3b289df4b7021c8d5
    public void paintComponent(Graphics g){
        super.paintComponent(g);


    }

    public ComponentDrawer getComponentDrawer(){
        return drawer;
    }

    public TikzComponent getComponent(){
        return drawer.getComponent();
    }


}
