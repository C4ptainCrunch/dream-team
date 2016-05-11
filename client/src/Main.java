import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import models.project.Project;
import utils.Dirs;
import utils.Log;
import views.accounts.LoginWindowView;
import views.editor.EditorView;
import views.management.ProjectManagementView;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);
    private static String token = getDiskToken();

    public static void main(String... args) {
        Log.init();
        if(args.length > 0 && args[0].equals("editor")){
            logger.info("Skip to the editor");
            java.awt.EventQueue.invokeLater(() -> {
                try {
                    new EditorView(new Project().getDiagram("unsaved"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else if (args.length > 0 && args[0].equals("project")) {
            logger.info("Skip to the projects");
            java.awt.EventQueue.invokeLater(() -> new ProjectManagementView());
        } else {
            logger.info("Starting project management view");
            java.awt.EventQueue.invokeLater(LoginWindowView::new);
        }

    }

    private static String getDiskToken() {
        Path tokenPath = Dirs.getDataDir().resolve(Paths.get("last-save.path"));
        try {
            return new String(Files.readAllBytes(tokenPath));
        } catch (IOException e) {
            return "";
        }
    }

    public static String getToken(){
        return token;
    }

    public static void setToken(String token) {
        Main.token = token;
    }
}
