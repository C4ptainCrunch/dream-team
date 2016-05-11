package views.editor;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import models.tikz.TikzComponent;
import models.tikz.TikzGraph;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import constants.Errors;
import constants.GUI;
import controllers.editor.SourceController;

/**
 * Implementation of the View (from the MVC architectural pattern) for the
 * Source. The Source is the area of the GUI where the Tikz is edited.
 *
 * @see <a href=http://javadoc.fifesoft.com/rsyntaxtextarea/org/fife/ui/rsyntaxtextarea/RSyntaxTextArea.html>RSyntaxTextArea doc</a>
 */
public class SourceView extends JPanel {
    private static final int TEXT_LINE_WIDTH = 40;

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
        this.controller = new SourceController(this, graph);
        this.textArea = new RSyntaxTextArea(0,TEXT_LINE_WIDTH);
        initPanel();
        initTextArea();
        setThemeToDefault();
    }

    /**
     * Initializes the panel of this view
     */
    private void initPanel() {
        this.setMaximumSize(this.getSize());
        this.setLayout(new GridLayout());
        RTextScrollPane sp = new RTextScrollPane(this.textArea);
        this.add(sp);

        this.addListeners();
    }

    /**
     * Initializes the text area with syntaxic color
     */
    private void initTextArea() {
        DisplayMode gd = GraphicsEnvironment.
                getLocalGraphicsEnvironment().
                getDefaultScreenDevice().
                getDisplayMode();
        int h = gd.getHeight();

        int nbOfLinesToSet = h/this.textArea.getLineHeight();
        this.textArea.setRows(nbOfLinesToSet);
        this.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LATEX);
        this.textArea.setCodeFoldingEnabled(true);
        this.controller.updateTikzText();
    }

    private void applyTheme(String file_path){
        try {
            InputStream stream = GUI.class.getClassLoader().getResourceAsStream(file_path);
            Theme theme = Theme.load(stream);
            theme.apply(this.textArea);
        } catch (IOException e){
            JOptionPane.showMessageDialog(this, Errors.LOAD_TEXT_AREA_ERROR, Errors.ERROR, JOptionPane.ERROR_MESSAGE);
        }
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

    private void highlightLine(Highlighter.HighlightPainter painter, int line_number){
        try {
            Highlighter highlighter = this.textArea.getHighlighter();
            highlighter.addHighlight(this.textArea.getLineStartOffset(line_number), this.textArea.getLineEndOffset(line_number), painter);
        } catch (BadLocationException e){
            e.printStackTrace();
        }
    }

    private Highlighter.HighlightPainter createHighlightPainter(){
        DefaultHighlighter.DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(this.textArea.getCurrentLineHighlightColor());
        return painter;
    }

    private void moveTextAreaCaretToRightPosition(int line_number){
        try {
            textArea.setCaretPosition(textArea.getLineStartOffset(line_number));
            textArea.setHighlightCurrentLine(true);
        } catch (BadLocationException e){
            e.printStackTrace();
        }
    }

    private void setThemeToDefault(){
        applyTheme(GUI.TextArea.DEFAULT_THEME);
    }

    private void setThemeToDaltonian(){
        applyTheme(GUI.TextArea.DEFAULT_COLOR_BLINDNESS_THEME);
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

    /**
     * Highlights the line corresponding to the given component
     *
     * @param comp
     *          The comp corresponding to the line to highlight
     */

    public void highlightCorrespondingLine(TikzComponent comp){
        if (comp != null) {
            int index = controller.getLine(comp);
            moveTextAreaCaretToRightPosition(index);
        }
    }

    /**
     * Highlights a zone in the text area corresponding to the given component sets.
     * @param selectedComponents
     *                      The set of components corresponding to the zone of the text area to highlight.
     */

    public void highlightCorrespondingZone(Set<TikzComponent> selectedComponents) {
        List<Integer> lines = controller.getLines(selectedComponents);
        Highlighter.HighlightPainter painter = createHighlightPainter();

        for (Integer line : lines){
            highlightLine(painter, line);
        }

        if (lines.size() > 0) {
            moveTextAreaCaretToRightPosition(lines.get(lines.size() - 1));
        }
    }

    public void removeHighlights() {
        Highlighter highlighter = textArea.getHighlighter();
        highlighter.removeAllHighlights();
        int end_line = textArea.getLineCount()-1;
        try {
            textArea.setCaretPosition(textArea.getLineStartOffset(end_line));
        } catch (BadLocationException e){
            e.printStackTrace();
        }
    }

    public void setColorBlindMode(boolean set_mode) {
        if (set_mode){
            setThemeToDaltonian();
        } else{
            setThemeToDefault();
        }
    }
}
