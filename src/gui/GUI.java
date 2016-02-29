package gui;

import javax.swing.*;
import java.awt.BorderLayout;

/**
 * Created by nikita on 2/29/16.
 */
public class GUI extends JFrame{
    public GUI() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // http://docs.oracle.com/javase/tutorial/uiswing/components/toplevel.html
        JPanel contentPane = new JPanel(new BorderLayout());

        this.pack();
        this.setVisible(true);
        setTitle("Test");
    }
}
