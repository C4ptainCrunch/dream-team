package views.editor.toolbox;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.MouseInputAdapter;

import constants.Models;
import models.ToolModel;
import controllers.editor.toolbox.AttributesChooserController;

/**
 * Implementation of the View (from the MVC architectural pattern) for the
 * AttributesChooser. The AttributesChooser is part of the toolbox used to
 * choose attributes for a particular component.
 */
public class AttributesChooserView extends JPanel {

    private static final int ATTRIBUTES_NUMBER = 3;
    private static final String COLOR_LABEL = "<HTML><U>Color</U></HTML>";
    private static final String STROKE_LABEL = "<HTML><U>Stroke width</U></HTML>";
    private static final String TITLE_LABEL = "<HTML><U>Label</U></HTML>";
    private final JPanel attributes;
    private final AttributesChooserController controller;
    private JScrollPane scrollzone;
    private JPanel color_chooser;
    private JTextField label_field;
    private JFormattedTextField stroke_width_field;

    /**
     * Constructs a new view for the AttributesChooser with a given ToolModel
     *
     * @param model
     *            the tool model
     */
    public AttributesChooserView(ToolModel model) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        scrollzone = new JScrollPane();
        attributes = new JPanel(new GridLayout(ATTRIBUTES_NUMBER * 2, 0)); // times
                                                                           // 2
                                                                           // because
                                                                           // of
                                                                           // the
                                                                           // describing
                                                                           // labels.
        scrollzone = new JScrollPane(attributes);
        this.add(scrollzone);
        controller = new AttributesChooserController(this, model);

        initColorChooser();
        initLabelField();
        initStrokeField();
    }

    /**
     * Adds a label to the view
     *
     * @param text
     *            The label
     */
    private void addLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        attributes.add(label);
    }

    /**
     * Adds listener on mouse click events for the color chooser.
     */
    private void addColorListener() {

        color_chooser.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                } else {
                    controller.chooseColor();
                }
            }
        });
    }

    /**
     * Adds listener on the label field
     */
    private void addLabelListener() {
        label_field.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                controller.labelEntered();
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                controller.labelEntered();
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                controller.labelEntered();
            }
        });
    }

    public String getLabel() {
        return label_field.getText();
    }
    public void setLabel(String text) {
        label_field.setText(text);
    }

    /**
     * Adds listener on the width field
     */
    private void addStrokeListener() {
        stroke_width_field.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                controller.strokeChanged();
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                controller.strokeChanged();
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                controller.strokeChanged();
            }
        });
    }

    public int getStrokeWidth() {
        try {
            return Integer.parseInt(stroke_width_field.getText());
        } catch (NumberFormatException e){
            return Models.DEFAULT.STROKE;
        }
    }

    /**
     * Initializes the color chooser
     */
    private void initColorChooser() {
        addLabel(COLOR_LABEL);
        color_chooser = new JPanel();
        color_chooser.setBackground(Color.WHITE);
        color_chooser.setBorder(new BevelBorder(BevelBorder.LOWERED));
        addColorListener();
        attributes.add(color_chooser);
        setColorFieldColor(Models.DEFAULT.COLOR);
    }

    /**
     * Initializes the label field
     */
    private void initLabelField() {
        addLabel(TITLE_LABEL);
        label_field = new JTextField();
        attributes.add(label_field);
        addLabelListener();
    }

    /**
     * Initializes the width field
     */
    private void initStrokeField() {
        addLabel(STROKE_LABEL);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        numberFormat.setGroupingUsed(false);
        stroke_width_field = new JFormattedTextField(numberFormat);
        attributes.add(stroke_width_field);
        addStrokeListener();
    }

    /**
     * Setter for the background color of the color chooser
     *
     * @param color
     *            The color
     */
    public void setColorFieldColor(Color color) {
        color_chooser.setBackground(color);
    }

    public Color getColor() {
        return color_chooser.getBackground();
    }

    public void setStroke(int stroke) {
        this.stroke_width_field.setText(Integer.toString(stroke));
    }
}
