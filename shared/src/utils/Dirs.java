package utils;

import java.nio.file.Path;
import java.nio.file.Paths;

import constants.Utils;

import javax.swing.filechooser.FileSystemView;

public class Dirs {
    public static Path getDataDir() {
        Path base = Paths.get(System.getProperty("user.home"));
        Path tail;

        switch (System.getProperty("os.name")) {
        case "Linux":
            tail = Paths.get(Utils.LINUX_PATH);
            break;
        case "Mac OS X":
            tail = Paths.get(Utils.MAC_PATH);
            break;
        default: // Windows
            tail = Paths.get(Utils.WINDOWS_PATH_ONE, System.getProperty("user.name"), Utils.WINDOWS_PATH_TWO);
            break;
        }

        return Paths.get(base.toString(), tail.toString());
    }

    public static Path getDefaultDirectory(){
        return FileSystemView.getFileSystemView().getDefaultDirectory().toPath();
    }
}
