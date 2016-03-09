package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.io.IOException;
import java.awt.Desktop;
import java.io.File;
import java.util.concurrent.TimeUnit;


public class GUI extends JFrame implements Menu.onItemClickListener {
    private static final Color button_background = new Color(238,238,238);
    private static final String icon_dir = "./././src/gui/toolbox/";
    private static final String[] node_icons = {"fill_circle.png", "fill_rect.png", "fill_triangle.png"};
    private static final String[] link_icons = {"fill_line.png", "arrow.png"};
    private static final String pathToTexFile="./././latex_files/test.tex";
    private static final String pathToPDFFile="./././pdf_files/test.pdf";

    private JPanel main_panel;
    private JScrollPane draw_zone;
    private JTextArea tikz_code_view;
    private JPanel link_button_pan;
    private JPanel node_button_pan;
    private JPanel caracteristikz_main_pan;
    private CaracteristikzOptionsPanel caracteristikz_options_pan;
    private TikzCanvas canvas;
    private Menu menu;
    private ButtonGroup button_group;

    public GUI(){
        initWindow();
        initUIComponents();
        this.setVisible(true);
    }

    private void initWindow(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(size);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("CreaTikZ");
        this.setContentPane(main_panel);
        setMouseListener();
    }

    private void setMouseListener(){            // Deselect a checkButton when right-click.
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (SwingUtilities.isRightMouseButton(mouseEvent)){
                    button_group.clearSelection();
                }

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    private void initUIComponents(){
        initCanvas();
        initMenu();
        node_button_pan.setLayout(new GridLayout(node_icons.length, 1));
        link_button_pan.setLayout(new GridLayout(link_icons.length, 1));
        caracteristikz_options_pan = new CaracteristikzOptionsPanel();
        caracteristikz_main_pan.add(caracteristikz_options_pan, BorderLayout.CENTER);
        initRadioGroup();
        initRadioButtons();
        addButtonsListener();
    }

    private void initRadioButtons() {
        initButtons(node_icons, node_button_pan);
        initButtons(link_icons, link_button_pan);
    }

    private void initButtons(String[] icons, JPanel pan){
        for (String icon : icons){
            JRadioButton button = new JRadioButton();
            Icon b_icon = new ImageIcon(icon_dir+icon);
            button.setIcon(b_icon);
            pan.add(button);
            button.setHorizontalAlignment(SwingConstants.CENTER);
            button_group.add(button);
        }
    }

    private void initMenu(){
        menu = new Menu(this);
        main_panel.add(menu, BorderLayout.NORTH);
    }

    private void initCanvas(){
        canvas = new TikzCanvas();
        main_panel.add(draw_zone, BorderLayout.CENTER);
        draw_zone.setViewportView(canvas);
    }

    private void initRadioGroup(){
        button_group = new ButtonGroup();
    }

    private void addButtonListener(AbstractButton button){
        button.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    button.setBackground(Color.LIGHT_GRAY);
                }
                else if (e.getStateChange() == ItemEvent.DESELECTED){
                    button.setBackground(button_background);
                }

            }
        });
    }

    private void addButtonsListener(){
        Enumeration<AbstractButton> buttons = button_group.getElements();
        while (buttons.hasMoreElements()){
            addButtonListener(buttons.nextElement());
        }
    }

    @Override
    public void onExit(){
        this.dispose();
    }

    @Override
    public void onShowGrid(){
//        canvas.setGridVisible(true);
        canvas.repaint();
    }

    @Override
    public void onHideGrid(){
//        canvas.setGridVisible(false);
        canvas.repaint();
    }

    @Override
    public void onBuildPDF(){

        File pdfDirectory = new File("pdf_files");

        // if the directory does not exist, create it
        if (!pdfDirectory.exists()) {
            try{
                pdfDirectory.mkdir();
            }
            catch(SecurityException se){
                // What should we log?
            }
        }

        try{
            Process p = Runtime.getRuntime().exec(new String[]{"pdflatex","-output-directory=pdf_files",pathToTexFile});
        } catch (IOException ex){
            // What should we log?
        }

        try{
            TimeUnit.SECONDS.sleep(1);
        } catch(InterruptedException ex){
            // What should we log?
        }

        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(pathToPDFFile);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // What should we log?
            }
        }
    }
}

