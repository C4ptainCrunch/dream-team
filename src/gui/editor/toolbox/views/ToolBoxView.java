package gui.editor.toolbox.views;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import models.tikz.TikzComponent;

public class ToolBoxView extends JPanel {

    private static final int BOX_WIDTH = 200;

    private final ToolView tikzComponentCreator;

    public ToolBoxView() {
        tikzComponentCreator = new ToolView();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
        this.add(tikzComponentCreator);
        this.setPreferredSize(new Dimension(BOX_WIDTH, this.getHeight()));
    }

    public TikzComponent getSelectedTool() {
        return tikzComponentCreator.getTikzComponent();
    }

    public void resetTool() {
        tikzComponentCreator.resetTool();
    }
}
