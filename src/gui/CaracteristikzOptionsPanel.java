package gui;


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaracteristikzOptionsPanel extends JPanel {
    private static final int ROW_COUNT = 4;
    private static final int COLUMN_COUNT = 1;

    private JButton color_chooser;
    private JLabel color_title;
    private JLabel stroke_size_title;

    public CaracteristikzOptionsPanel(){
        this.setLayout(new GridLayout(ROW_COUNT, COLUMN_COUNT));
        this.setBorder(new EtchedBorder());
        initColorTitle();
        initColorChooser();
        addColorChooserListener();
    }

    private void initColorTitle(){
        color_title = new JLabel("<HTML><U>Stroke Color</U></HTML>");
        this.add(color_title, 0);
    }

    private void initColorChooser(){
        color_chooser = new JButton();
        color_chooser.setBackground(Color.BLACK);
        color_chooser.setBorderPainted(false);
        this.add(color_chooser, 1);

    }

    private void addColorChooserListener(){
        color_chooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color color = JColorChooser.showDialog(CaracteristikzOptionsPanel.this, "Choose Stroke Color",
                        color_chooser.getBackground());
                if (color != null) {
                    color_chooser.setBackground(color);
                }
            }
        });
    }
}
