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

/**
 * FileChooseView is a generic File chooser with some small modifications (for
 * example: trying to show a recently opened directory by default)
 */
public class FileChooseView extends JPanel {
    JFileChooser chooser;

    /**
     * Constructs a JFileChooser with a given title and selection mode.
     *
     * @param title
     *            Title for the JFileChooser
     * @param selectionMode
     *            Selection mode for the JFileChooser
     */
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

    private static Path getRecentPath() {
        return Dirs.getDataDir().resolve(Paths.get("last-save.path"));
    }

    /**
     * Get the "recent" directory stored.
     *
     * @return Path to the last directory of the last file that was saved.
     * @throws IOException
     */
    public static Path getRecent() throws IOException {
        Path p = Paths.get(new String(Files.readAllBytes(getRecentPath())));
        if (!p.toFile().exists()) {
            throw new IOException("Recent path does not exist anymore");
        }
        return p;
    }

    /**
     * Set a directory as "recent", storing it to open the JFileChooser there
     * next time.
     *
     * @param fPath
     *            Path to the last file that was saved
     * @throws IOException
     */
    public static void setRecent(File fPath) throws IOException {
        String line = fPath.toPath().getParent().toAbsolutePath().toString();
        Files.write(getRecentPath(), line.getBytes(), TRUNCATE_EXISTING, CREATE);
    }

    /**
     * Request a specific file
     *
     * @return The requested file
     */
    public File ask() {
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }

    /**
     * Sets a file type restriction for the JFileChooser, showing by default
     * only files of "fileExtension" type.
     *
     * @param fileType
     *            Description of the file type
     * @param fileExtension
     *            File extension to filter
     */
    public void setFileRestriction(String fileType, String fileExtension) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(fileType, fileExtension);
        chooser.setFileFilter(filter);
    }

    /**
     * Sets the selected file in the JFileChosser
     *
     * @param f
     *            File to be selected
     */
    public void setSelectedFile(File f) {
        chooser.setSelectedFile(f);
    }
}
