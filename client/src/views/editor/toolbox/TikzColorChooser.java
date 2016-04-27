package views.editor.toolbox;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

public class TikzColorChooser {

    /**
     * Opens a color picker that only accepts valid tikz colors
     * @param base the selected color on window opening
     * @return the selected color
     */
    public static Color choose(Color base){
        JColorChooser chooser = new JColorChooser(base);
        AbstractColorChooserPanel[] panel = {new TikzColorChooserPanel()};
        chooser.setChooserPanels(panel);
        JDialog dialog = JColorChooser.createDialog(null, "Chose your color", true, chooser, null, null);
        dialog.setVisible(true);
        return chooser.getColor();
    }
}
