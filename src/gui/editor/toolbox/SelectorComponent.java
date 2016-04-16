package gui.editor.toolbox;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import models.TikzComponent;
import gui.editor.views.canvas.drawables.DrawableTikzComponent;
import gui.editor.views.canvas.drawers.Drawer;

public class SelectorComponent extends JPanel {
    private final TikzComponent component;
    private final SelectorComponentListener listener;

    public SelectorComponent(TikzComponent comp, SelectorComponentListener lis) {
        component = comp;
        listener = lis;
        initMouseListener();
    }

    private void initMouseListener() {
        this.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                    listener.componentSelected(component);
                }
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        DrawableTikzComponent drawable = Drawer.toDrawable(component);
        drawable.tikz2swing(this);
        drawable.draw((Graphics2D) g);
    }

    public interface SelectorComponentListener {
        void componentSelected(TikzComponent component);
    }
}
