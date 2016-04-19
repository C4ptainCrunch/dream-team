package views.editor.canvas;

import models.ToolModel;
import models.tikz.TikzComponent;
import views.editor.toolbox.AttributesChooserView;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by mrmmtb on 18.04.16.
 */
public class NodeEditionView extends JFrame {

    private AttributesChooserView modifierView;
    private TikzComponent component;
    private JButton doneButton;

    public NodeEditionView(TikzComponent component) {
        this.component=component;
        initWindow();
        initButton();

        this.setPreferredSize(new Dimension(250,300));
        this.pack();
        this.getContentPane().setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void initWindow() {
        this.modifierView = new AttributesChooserView(new ToolModel(component));
        this.getContentPane().add(modifierView, BorderLayout.CENTER);
    }

    public void initButton() {
        this.doneButton = new JButton("Done");
        this.doneButton.addActionListener(actionEvent -> this.dispose());
        this.getContentPane().add(this.doneButton, BorderLayout.SOUTH);
    }
}
