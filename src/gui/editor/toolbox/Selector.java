package gui.editor.toolbox;

import javax.swing.*;
import java.awt.*;

/**
 * Created by aurelien on 12/04/16.
 */
public class Selector extends JPanel {

    private JScrollPane scrollzone;
    private JPanel options;

    public Selector(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        options = new JPanel(new GridLayout(15,0));
        scrollzone = new JScrollPane(options);
        this.add(scrollzone);

        testScrolling();
    }

    private void testScrolling(){

        for (int i = 0; i < 15; i++){
            options.add(new JButton("Test" + Integer.toString(i)));
        }
    }
}
