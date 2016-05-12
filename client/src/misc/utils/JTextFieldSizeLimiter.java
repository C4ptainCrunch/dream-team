package misc.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Allows to add a length limit to a JTextField via the setDocument() function.
 * This avoids trying to insert something that is bigger than the size set in the
 * database field.
 */

public class JTextFieldSizeLimiter extends PlainDocument {
    private int fieldLimit;

    public JTextFieldSizeLimiter (final int limit) {
        super();
        this.fieldLimit = limit;
    }

    /**
     * Allows to block further writing when the JTextField is full
     * @param offset The offset between the string and set size
     * @param str The current string
     * @param attr Attribute
     */
    public void insertString(final int offset, final String str, final AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return ;
        }
        if ((getLength() + str.length()) <= this.fieldLimit) {
            super.insertString(offset,str,attr);
        }
    }
}
