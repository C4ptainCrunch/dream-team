package gui.editor.toolbox;

import gui.editor.views.canvas.drawables.Drawable;
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

    private ComponentDrawer drawer;
    private TikzComponent component;
    private int shape_size;
    private SelectorComponentListener listener;

    public SelectorComponent(ComponentDrawer d, TikzComponent comp, SelectorComponentListener lis){
        drawer = d;
        component = comp;
        listener = lis;

        initMouseListener();
    }

    public interface SelectorComponentListener{
        void componentSelected(ComponentDrawer draw);
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
            else if(component instanceof TikzEdge){
                Point midEdge = ((TikzEdge)component).getPosition();
                // We center the edge, so we need to take the opposite of the center to start the drawing. This needs a huge refactor hehe
                midEdge.setLocation(-midEdge.getX(), -midEdge.getY());
                drawable.translate(new Point(midEdge), this);
            }
            drawable.draw((Graphics2D)(g));
        }
    }
}
