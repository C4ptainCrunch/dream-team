package misc.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldSizeLimiter extends PlainDocument {
    private int fieldLimit;

    public JTextFieldSizeLimiter (int limit) {
        super();
        this.fieldLimit = limit;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return ;
        }
        if ((getLength() + str.length()) <= this.fieldLimit) {
            super.insertString(offset,str,attr);
        }
    }
}
