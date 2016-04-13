package gui.projectManagement.controllers;


import gui.projectManagement.views.HistoryView;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HistoryController {
    private HistoryView view;
    private String path;

    public HistoryController(HistoryView view, String path){
        this.path = path;
        this.view = view;
    }

    public void fillView() {
        try{
            byte[] encoded = Files.readAllBytes(Paths.get(path+"/diffs"));
            String res = new String(encoded, "UTF-8");
            //view.getHistoryPane().replaceSelection(res);
            appendString(res, Color.BLUE);
            appendString("testilol", Color.RED);

        }catch(IOException e){
            e.getStackTrace();
        }
    }

    private void appendString(String str, Color color){
        StyledDocument document = (StyledDocument) view.getHistoryPane().getDocument();
        Style style = document.addStyle("color",null);
        StyleConstants.setForeground(style, color);
        try {
            document.insertString(document.getLength(), str, style);
        }catch(BadLocationException e){
            e.getStackTrace();
        }
    }
}
