package gui.editor.toolbox;

import gui.editor.views.canvas.drawers.Drawer;
import models.TikzComponent;
import gui.editor.views.canvas.drawers.ComponentDrawer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by aurelien on 12/04/16.
 */
public class Selector extends JPanel implements SelectorComponent.SelectorComponentListener {
    private JScrollPane scrollzone;
    private JPanel options;
    private SelectorListener listener;

    public Selector(SelectorListener lis) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        options = new JPanel(new GridLayout(1, 0));
        scrollzone = new JScrollPane(options);
        listener = lis;
        this.add(scrollzone);
    }

    public interface SelectorListener{
        void componentSelected(TikzComponent component);
    }

    protected void addComponent(SelectorComponent comp){
        options.add(comp);
    }

    public void setComponentNbr(int nbr){
        ((GridLayout)options.getLayout()).setRows(nbr);
    }

    @Override
    public void componentSelected(TikzComponent component){
        listener.componentSelected(component);

    }
}
