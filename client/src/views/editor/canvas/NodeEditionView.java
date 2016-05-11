package views.editor.canvas;

import java.awt.*;

import javax.swing.*;

import models.ToolModel;
import models.tikz.TikzComponent;
import views.editor.toolbox.AttributesChooserView;

/**
 * A dialog view that provides modification tools in order to modify a
 * TikzComponent.
 */

public class NodeEditionView extends JDialog {

    private AttributesChooserView modifierView;
    private TikzComponent component;
    private JButton doneButton;

    public NodeEditionView(TikzComponent component) {
        this.component = component;
        initWindow();
        initButton();

        this.setPreferredSize(new Dimension(250, 300));
        this.pack();
        this.getContentPane().setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void initWindow() {
        this.modifierView = new AttributesChooserView(new ToolModel(component));
        this.modifierView.setColorFieldColor(component.getStrokeColor());
        this.modifierView.setLabel(component.getLabel());
        this.modifierView.setStroke(component.getStroke());
        this.getContentPane().add(modifierView, BorderLayout.CENTER);
    }

    public void initButton() {
        this.doneButton = new JButton("Done");
        this.doneButton.addActionListener(actionEvent -> this.dispose());
        this.getContentPane().add(this.doneButton, BorderLayout.SOUTH);
    }
}
