package views.editor.toolbox;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import parser.TikzColors;

/**
 * A swatch-like color chooser that accepts only valid Tikz colors
 */
public class TikzColorChooserPanel extends AbstractColorChooserPanel {
    Map<Color, JToggleButton> buttons = new HashMap<>();

    /**
     * Method called when the user clicks on a color.
     */
    public void updateChooser() {
        Color color = getColorFromModel();
        JToggleButton button = buttons.get(color);
        button.setSelected(true);
    }

    protected JToggleButton createColorButton(Color color) {
        JToggleButton button = new JToggleButton();
        button.setBackground(color);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(40, 40));
        return button;
    }

    protected void buildChooser() {
        Set<Color> tikzColors = TikzColors.getTikzColors();
        int square = (int) Math.sqrt(tikzColors.size());

        setLayout(new GridLayout(square / 2, square * 2));
        ButtonGroup group = new ButtonGroup();

        for(Color color: tikzColors){
            JToggleButton b = createColorButton(color);
            b.addActionListener(e -> getColorSelectionModel().setSelectedColor(color));
            this.buttons.put(color, b);
            group.add(b);
            add(b);
        }
    }

    public String getDisplayName() {
        return "Tikz colors";
    }

    public Icon getSmallDisplayIcon() {
        return null;
    }

    public Icon getLargeDisplayIcon() {
        return null;
    }
}

