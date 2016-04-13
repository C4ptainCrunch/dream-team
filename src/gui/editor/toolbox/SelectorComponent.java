package gui.editor.toolbox;

import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawers.Drawer;
import models.TikzComponent;
import models.TikzNode;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class SelectorComponent extends JPanel {

    private Drawer drawer;
    private TikzComponent component;
    private int shape_size;
    private SelectorComponentListener listener;

    public SelectorComponent(Drawer d, TikzComponent comp, SelectorComponentListener lis){
        drawer = d;
        component = comp;
        listener = lis;

        initMouseListener();
    }

    public interface SelectorComponentListener{
        void componentSelected(Drawer draw);
    }

    private void initMouseListener(){
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(SwingUtilities.isLeftMouseButton(mouseEvent)){
                    listener.componentSelected(drawer);
                }
            }
        });
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for (Drawable drawable: drawer.toDrawable()){
            if(component instanceof TikzNode){
                drawable.translate(new Point(((TikzNode)component).getPosition()), this);
            }
            drawable.draw((Graphics2D)(g));
        }
    }
}
