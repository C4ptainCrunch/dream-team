package views.editor;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import models.tikz.TikzGraph;
import controllers.editor.SourceController;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * Implementation of the View (from the MVC architectural pattern) for the
 * Source. The Source is the area of the GUI where the Tikz is edited.
 */
public class SourceView extends JPanel {
    private static final int TEXT_AREA_WIDTH = 300;

    private final TikzGraph graph;
    private final RSyntaxTextArea textArea;
    private final SourceController controller;
    private final EditorView parentView;
    private Boolean isFocused = false;

    /**
     * Constructs a new View for the Source, with a given TikzGraph and
     * parentView
     *
     * @param parentView
     *            The view which contains this view (ie. EditorView)
     * @param graph
     *            The TikzGraph
     */
    public SourceView(EditorView parentView, TikzGraph graph) {
        this.parentView = parentView;
        this.graph = graph;


        DisplayMode gd = GraphicsEnvironment.
                getLocalGraphicsEnvironment().
                getDefaultScreenDevice().
                getDisplayMode();
        int h = gd.getHeight();
        int w = gd.getWidth();
        System.out.println("height: "+h+", width : "+w);


        this.textArea = new RSyntaxTextArea(0,50);
        int nbOfLinesToSet = h/this.textArea.getLineHeight()-3;
        this.textArea.setRows(nbOfLinesToSet);
        this.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
        this.textArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(this.textArea);
        this.add(sp);
        this.controller = new SourceController(this, graph);

        /*
        this.parentView.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
            }

            @Override
            public void componentMoved(ComponentEvent componentEvent) {

            }

            @Override
            public void componentShown(ComponentEvent componentEvent) {

            }

            @Override
            public void componentHidden(ComponentEvent componentEvent) {

            }
        });
        */

        this.addListeners();
    }


    /**
     * Adds listeners for focus and document events to the view Reacts to a
     * loss/gain of focus Reacts to a modification to the text area
     */
    private void addListeners() {
        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                controller.focusGained();
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                controller.focusLost();
            }
        });

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                controller.updateGraphIfFocused();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                controller.updateGraphIfFocused();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                controller.updateGraphIfFocused();
            }
        });
    }

    /**
     * Returns true if this view is focused
     *
     * @return true if this view is focused
     */
    public Boolean getIsFocused() {
        return isFocused;
    }

    /**
     * Sets whether the view is focused or not
     *
     * @param isFocused
     *            The boolean to set whether the view is focused or not
     */
    public void setIsFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }

    /**
     * Getter for the text area which contains the tikz text
     *
     * @return the tikz text contained in the text area
     */
    public String getText() {
        return textArea.getText();
    }

    /**
     * Setter for the text area which contains the tikz text
     *
     * @param text
     *            The tikz text which will be contained in the text area
     */
    public void setText(String text) {
        textArea.setText(text);
    }
}
