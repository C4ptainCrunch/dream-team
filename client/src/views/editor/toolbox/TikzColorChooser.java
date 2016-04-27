package views.editor.toolbox;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

public class TikzColorChooser {
    public static Color choose(Color base){
        JColorChooser chooser = new JColorChooser(base);
        AbstractColorChooserPanel[] panel = {new TikzColorChooserPanel()};
        chooser.setChooserPanels(panel);
        JDialog dialog = JColorChooser.createDialog(null, "Chose your color", true, chooser, null, null);
        dialog.setVisible(true);
        return chooser.getColor();
    }
}
