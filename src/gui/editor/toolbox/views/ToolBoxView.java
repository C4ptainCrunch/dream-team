package gui.editor.toolbox.views;


import gui.editor.views.canvas.drawers.ComponentDrawer;
import models.TikzComponent;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class ToolBoxView extends JPanel {

    private static final int BOX_WIDTH = 200;

    private ToolView creator;


    public ToolBoxView(){
        creator = new ToolView();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
        this.add(creator);
        this.setPreferredSize(new Dimension(BOX_WIDTH, this.getHeight()));
    }

    public TikzComponent getSelectedTool(){
        return creator.getTikzComponent();
    }
}
