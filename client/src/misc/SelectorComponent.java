package misc;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import models.tikz.TikzComponent;
import views.editor.canvas.drawables.DrawableTikzComponent;
import views.editor.canvas.drawers.Drawer;

/**
 * Represents a tikz component being drawn in the Selector
 */
public class SelectorComponent extends JPanel {
    private final TikzComponent component;
    private final SelectorComponentListener listener;

    /**
     * Constructs a new selector component
     * with a given tikz component and listener
     * @param comp The tikz component
     * @param lis The listener
     */
    public SelectorComponent(TikzComponent comp, SelectorComponentListener lis) {
        component = comp;
        listener = lis;
        initMouseListener();
    }

    /**
     * Initializes the mouse listener to know
     * which component has been clicked on
     */
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

    /**
     * Paints the component on the view
     * @param g The Graphics to be painted
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        DrawableTikzComponent drawable = Drawer.toDrawable(component, this);
        drawable.draw((Graphics2D) g);
    }

    public interface SelectorComponentListener {
        void componentSelected(TikzComponent component);
    }
}
