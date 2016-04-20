package views.editor.toolbox;

import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import models.tikz.TikzComponent;
import views.editor.toolbox.templateview.TemplateToolView;

/**
 * Implementation of the View (from the MVC architectural pattern) for the ToolBox.
 */
public class ToolBoxView extends JPanel {

    private static final int BOX_WIDTH = 200;
    private static final String SHAPE_TAB = "<html>S<br>H<br>A<br>P<br>E<br>S</html>";
    private static final String TEMPLATE_TAB = "<html>T<br>E<br>M<br>P<br>L<br>A<br>T<br>E<br>S</html>";

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

    private void initTabs(){
        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setTabPlacement(JTabbedPane.LEFT);
        this.tabbedPane.addTab(SHAPE_TAB, this.tikzComponentCreator);
        this.tabbedPane.addTab(TEMPLATE_TAB, this.tikzTemplateCreator);

        this.tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        this.add(tabbedPane);
    }

    /**
     * Getter for the selected tool
     * @return The selected tool
     */
    public TikzComponent getSelectedTool() {
        return tikzComponentCreator.getTikzComponent();
    }

    public void resetTool() {
        tikzComponentCreator.resetTool();
    }

    public void addTemplateToView(File file){
        tikzTemplateCreator.addTemplateFromFile(file);
    }
}
