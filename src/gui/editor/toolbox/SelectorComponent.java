package gui.editor.toolbox;

import gui.editor.views.canvas.drawables.Drawable;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;
import gui.editor.views.canvas.drawers.ComponentDrawer;
import gui.editor.views.canvas.drawers.Drawer;
import models.TikzComponent;
import models.TikzEdge;
import models.TikzNode;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class SelectorComponent extends JPanel {
    private TikzComponent component;
    private SelectorComponentListener listener;

    public SelectorComponent(TikzComponent comp, SelectorComponentListener lis){
        component = comp;
        listener = lis;
        initMouseListener();
    }

    public interface SelectorComponentListener{
        void componentSelected(TikzComponent component);
    }

    private void initMouseListener(){
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(SwingUtilities.isLeftMouseButton(mouseEvent)){
                    listener.componentSelected(component);
                }
            }
        });
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        DrawableTikzComponent drawable = Drawer.toDrawable(component);
        System.out.println(drawable.getComponent().getClass().getName() + " stroke : " + (drawable.getStroke() == null));
        drawable.draw((Graphics2D)g);
    }
}
