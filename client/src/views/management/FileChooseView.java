package views.management;

import utils.Dirs;

import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooseView extends JPanel {
    JFileChooser chooser;

    public FileChooseView(String title, int selectionMode) {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(Dirs.getDefaultDirectory().toFile());
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(selectionMode);
    }

    public File ask() {
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }

    public void setFileRestriction(String fileType, String fileExtension) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(fileType, fileExtension);
        chooser.setFileFilter(filter);
    }
}
