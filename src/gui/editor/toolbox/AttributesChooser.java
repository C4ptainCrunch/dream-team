package gui.editor.toolbox;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

public class AttributesChooser extends JPanel {

    private static final int ATTRIBUTES_NUMBER = 3;
    private static final String COLOR_LABEL = "<HTML><U>Color</U></HTML>";
    private static final String STROKE_LABEL = "<HTML><U>Stroke width</U></HTML>";
    private static final String TITLE_LABEL = "<HTML><U>Label</U></HTML>";

    private JScrollPane scrollzone;
    private JPanel attributes;
    private JPanel color_chooser;
    private Color chosen_color;
    private JTextField label_field;
    private JTextField stroke_width_field;

    public AttributesChooser(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        scrollzone = new JScrollPane();
        attributes = new JPanel(new GridLayout(ATTRIBUTES_NUMBER*2, 0));    // times 2 because of the describing labels.
        scrollzone = new JScrollPane(attributes);
        this.add(scrollzone);

        initColorChooser();
        initLabelField();
        initStrokeField();
    }

    private void addLabel(String text){
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        attributes.add(label);
    }

    private void addColorListener(){

        color_chooser.addMouseListener(new MouseInputAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                if(SwingUtilities.isRightMouseButton(e)){}
                else {
                    chosen_color = JColorChooser.showDialog(AttributesChooser.this, "Choose Stroke Color",
                            color_chooser.getBackground());
                    color_chooser.setBackground(chosen_color);
                }
            }
        });
    }

    private void initColorChooser(){
        addLabel(COLOR_LABEL);
        color_chooser = new JPanel();
        color_chooser.setBackground(Color.WHITE);
        color_chooser.setBorder(new BevelBorder(BevelBorder.LOWERED));
        addColorListener();
        attributes.add(color_chooser);
    }

    private void initLabelField(){
        addLabel(TITLE_LABEL);
        label_field = new JTextField();
        attributes.add(label_field);
    }

    private void initStrokeField(){
        addLabel(STROKE_LABEL);
        stroke_width_field = new JTextField();
        attributes.add(stroke_width_field);
    }
}
