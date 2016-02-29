package gui;

import javax.swing.*;
import java.awt.BorderLayout;


public class GUI extends JFrame{
    public GUI() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // http://docs.oracle.com/javase/tutorial/uiswing/components/toplevel.html
        JPanel window = new JPanel(new BorderLayout());

        JPanel drawingPanel = new JPanel();
        JPanel texPanel = new JPanel();

        JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, drawingPanel, texPanel);
        splitPanel.setResizeWeight(0.5);

        window.add(splitPanel);

        // add the window to the JFrame
        this.setContentPane(window);

        this.pack();
        this.setVisible(true);
        setTitle("Test");
    }
}
