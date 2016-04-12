package gui.editor.toolbox.views;


import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * Created by aurelien on 12/04/16.
 */
public class Preview extends JPanel {


    public Preview(){
        this.setBackground(Color.WHITE);
        this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
    }
}
