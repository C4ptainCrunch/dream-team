package legacy;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CaracteristikzOptionsPanel extends JPanel {
    private static final int ROW_COUNT = 7;
    private static final int COLUMN_COUNT = 1;
    private static final int MIN_WIDTH = 1;
    private static final int MAX_WIDTH = 100;

    private JButton color_chooser;
    private JLabel color_title;
    private JLabel stroke_width_title;
    private JLabel node_label_title;
    private JSlider stroke_width_slider;
    private JFormattedTextField stroke_width_entry;
    private JTextField node_label_entry;
    private Color chosen_color = Color.black;

    public CaracteristikzOptionsPanel(){
        this.setLayout(new GridLayout(ROW_COUNT, COLUMN_COUNT));
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));

        initColorOptions();
        initStrokeOptions();
        initNodeLabelOptions();
    }

    private void initColorOptions(){
        initColorTitle();
        initColorChooser();
        addColorChooserListener();
    }

    private void initStrokeOptions(){
        initStrokeWidthTitle();
        initStrokeWidthEntry();
        initStrokeWidthSlider();
        addStrokeWidthEntryListener();
    }

    private void initNodeLabelOptions(){
        initNodeLabelTitle();
        initNodeLabelEntry();
    }

    private void initColorTitle(){
        color_title = new JLabel("<HTML><U>Stroke Color</U></HTML>");
        this.add(color_title, 0);
    }

    private void initStrokeWidthSlider(){
        stroke_width_slider = new JSlider(MIN_WIDTH, MAX_WIDTH, MIN_WIDTH);            // Third arg is initial value.
        this.add(stroke_width_slider, 4);
        stroke_width_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int value = stroke_width_slider.getValue();
                stroke_width_entry.setText(Integer.toString(value));
            }
        });
    }

    private void initStrokeWidthEntry(){
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        numberFormat.setGroupingUsed(false);
        stroke_width_entry = new JFormattedTextField(numberFormat);
        stroke_width_entry.setText(Integer.toString(MIN_WIDTH));
        this.add(stroke_width_entry, 3);
    }

    private void addStrokeWidthEntryListener(){
        stroke_width_entry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String value = stroke_width_entry.getText();
                stroke_width_slider.setValue(Integer.parseInt(value));
            }
        });
    }

    private void initStrokeWidthTitle(){
        stroke_width_title = new JLabel("<HTML><U>Stroke Width</U></HTML>");
        this.add(stroke_width_title, 2);
    }

    private void initColorChooser(){
        color_chooser = new JButton();
        color_chooser.setBackground(chosen_color);
        color_chooser.setBorderPainted(false);
        this.add(color_chooser, 1);

    }

    private void addColorChooserListener(){
        color_chooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chosen_color = JColorChooser.showDialog(CaracteristikzOptionsPanel.this, "Choose Stroke Color",
                        color_chooser.getBackground());
                if (chosen_color != null) {
                    color_chooser.setBackground(chosen_color);
                }
            }
        });
    }

    private void initNodeLabelTitle(){
        node_label_title = new JLabel("<HTML><U>Node Label</U><HTML>");
        this.add(node_label_title, 5);
    }

    private void initNodeLabelEntry(){
        node_label_entry = new JTextField();
        this.add(node_label_entry, 6);
    }

    public String getNodeLabel(){
        return node_label_entry.getText();
    }

    public Color getChosenColor(){
        return chosen_color;
    }

    public int getStrokeWidth() {
        return stroke_width_slider.getValue();
    }
}
