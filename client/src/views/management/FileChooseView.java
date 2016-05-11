package views.management;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import utils.Dirs;

public class FileChooseView extends JPanel {
    JFileChooser chooser;

    public FileChooseView(String title, int selectionMode) {
        chooser = new JFileChooser();
        try {
            chooser.setCurrentDirectory(this.getRecent().toFile());
        } catch (IOException e) {
            chooser.setCurrentDirectory(Dirs.getDefaultDirectory().toFile());
        }
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(selectionMode);
    }

    /**
     * Request a specific file
     * @return The requested file
     */
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

    public void setSelectedFile(File f){
        chooser.setSelectedFile(f);
    }

    private static Path getRecentPath() {
        return Dirs.getDataDir().resolve(Paths.get("last-save.path"));
    }

    public static void setRecent(File fPath) throws IOException {
        String line = fPath.toPath().getParent().toAbsolutePath().toString();
        Files.write(getRecentPath(), line.getBytes(), TRUNCATE_EXISTING, CREATE);
    }

    public static Path getRecent() throws IOException {
        Path p = Paths.get(new String(Files.readAllBytes(getRecentPath())));
        if(!p.toFile().exists()) {
            throw new IOException("Recent path does not exist anymore");
        }
        return p;
    }
}
