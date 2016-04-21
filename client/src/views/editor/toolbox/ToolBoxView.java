package views.editor.toolbox;

import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import constants.GUI;
import models.tikz.TikzComponent;
import views.editor.toolbox.templateview.TemplateToolView;

/**
 * Implementation of the View (from the MVC architectural pattern) for the
 * ToolBox.
 */
public class ToolBoxView extends JPanel {

    private static final int BOX_WIDTH = 200;

    private final ToolView tikzComponentCreator;
    private final TemplateToolView tikzTemplateCreator;
    private JTabbedPane tabbedPane;

    /**
     * Constructs a new view for the ToolBox
     */
    public ToolBoxView() {
        tikzComponentCreator = new ToolView();
        tikzTemplateCreator = new TemplateToolView();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
        this.setPreferredSize(new Dimension(BOX_WIDTH, this.getHeight()));
        initTabs();
    }

    private void initTabs() {
        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setTabPlacement(JTabbedPane.LEFT);
        this.tabbedPane.addTab(GUI.Tabs.SHAPE_TAB, this.tikzComponentCreator);
        this.tabbedPane.addTab(GUI.Tabs.TEMPLATE_TAB, this.tikzTemplateCreator);

        this.tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        this.add(tabbedPane);
    }

    /**
     * Getter for the selected tool
     *
     * @return The selected tool
     */
    public TikzComponent getSelectedTool() {
        return tikzComponentCreator.getTikzComponent();
    }

    public void resetTool() {
        tikzComponentCreator.resetTool();
    }

    public void addTemplateToView(File file) {
        tikzTemplateCreator.addTemplateFromFile(file);
    }
}
