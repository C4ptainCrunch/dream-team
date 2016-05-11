import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import controllers.accounts.LoginWindowController;
import models.project.Project;
import utils.Dirs;
import utils.Log;
import views.accounts.LoginWindowView;
import views.editor.EditorView;
import views.management.ProjectManagementView;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);

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
            java.awt.EventQueue.invokeLater(ProjectManagementView::new);
        } else {
            logger.info("Starting login window");
            if(LoginWindowController.shouldSkipAuth()){
                java.awt.EventQueue.invokeLater(ProjectManagementView::new);
            } else {
                java.awt.EventQueue.invokeLater(LoginWindowView::new);
            }
        }

    }
}
