package gui;


import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class CaracteristikzOptionsPanel extends JPanel {
    private static final int ROW_COUNT = 5;
    private static final int COLUMN_COUNT = 1;
    private static final int MIN_WIDTH = 1;
    private static final int MAX_WIDTH = 100;

    private JButton color_chooser;
    private JLabel color_title;
    private JLabel stroke_width_title;
    private JSlider stroke_width_slider;
    private JFormattedTextField stroke_width_entry;

    public CaracteristikzOptionsPanel(){
        this.setLayout(new GridLayout(ROW_COUNT, COLUMN_COUNT));
        this.setBorder(new EtchedBorder());

        initColorOptions();
        initStrokeOptions();
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
