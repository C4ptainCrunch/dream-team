package views.help;

import java.awt.*;

import javax.swing.*;

import org.pegdown.PegDownProcessor;

import controllers.help.HelpController;

/**
 * Implementation of the View (from the MVC architectural pattern) for the Help.
 * The Help Displays a Tree architecture of help files that can be consulted
 */
public class HelpView extends JFrame {
    private final HelpController controller;
    private JTree tree;
    private JLabel htmlView;

    /**
     * Constructs a new view for the Help
     */
    public HelpView() {
        this.controller = new HelpController(this);
        this.render();
        this.addListeners();
        this.setVisible(true);
    }

    /**
     * Adds listeners for selected help files
     */
    private void addListeners() {
        this.tree.getSelectionModel().addTreeSelectionListener(this.controller::treeClicked);
    }

    /**
     * Renders the view with the tree help files
     */
    private void render() {
        this.setTitle("Help");
        this.getContentPane().setPreferredSize(new Dimension(800, 400));

        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(new BoxLayout(helpPanel, BoxLayout.X_AXIS));

        UIManager.put("Tree.rendererFillBackground", false);

        this.tree = new JTree(controller.getTree());
        this.tree.setSize(new Dimension(60, 400));
        this.tree.setOpaque(false);
        for (int i = 0; i < this.tree.getRowCount(); i++) {
            this.tree.expandRow(i);
        }
        helpPanel.add(this.tree);

        this.htmlView = new JLabel();
        this.htmlView.setSize(new Dimension(700, 400));
        helpPanel.add(new JScrollPane(this.htmlView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

        this.add(helpPanel);
        this.pack();
    }

    /**
     * Setter for the text to be displayed that comes from the selected help
     * file
     *
     * @param text
     *            The help text
     */
    public void setText(String text) {
        PegDownProcessor pegDown = new PegDownProcessor();

        htmlView.setText("<html>" + pegDown.markdownToHtml(text) + "</html>");
    }
}
