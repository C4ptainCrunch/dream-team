package views.editor.toolbox;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import models.tikz.TikzComponent;

/**
 * Implementation of the View (from the MVC architectural pattern) for the ToolBox.
 */
public class ToolBoxView extends JPanel {

    private static final int BOX_WIDTH = 200;

    private final ToolView tikzComponentCreator;

    /**
     * Constructs a new view for the ToolBox
     */
    public ToolBoxView() {
        tikzComponentCreator = new ToolView();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
        this.add(tikzComponentCreator);
        this.setPreferredSize(new Dimension(BOX_WIDTH, this.getHeight()));
    }

    /**
     * Getter for the selected tool
     * @return The selected tool
     */
    public TikzComponent getSelectedTool() {
        return tikzComponentCreator.getTikzComponent().getClone();
    }
}
