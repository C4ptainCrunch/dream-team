package views.editor.toolbox;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

public class TikzColorChooser {
    public static Color choose(Color base){
        JColorChooser chooser = new JColorChooser(base);
        AbstractColorChooserPanel[] panel = {new TikzColorChooserPanel()};
        chooser.setChooserPanels(panel);
        chooser.show();
        JDialog dialog = JColorChooser.createDialog(null, "Chose your color", true, chooser, null, null);
        dialog.show();
        System.out.println(chooser.getColor());
        return chooser.getColor();
    }
}
