package gui.editor.toolbox.views;


import javax.swing.*;
import java.awt.*;

public class ToolBoxView extends JPanel {

    private static final int BOX_WIDTH = 200;

    private ToolView creator;


    public ToolBoxView(){
        creator = new ToolView();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(creator);
        this.setPreferredSize(new Dimension(BOX_WIDTH, this.getHeight()));
    }
}
