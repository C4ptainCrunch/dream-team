import java.io.IOException;
import java.util.logging.Logger;

import models.project.Project;
import utils.Log;
import views.accounts.LoginWindowView;
import views.editor.EditorView;
import views.management.ManagementView;
import constants.ClientPropertiesLoader;
import controllers.accounts.LoginWindowController;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);

    public static void main(String... args) {
        Log.init();
        ClientPropertiesLoader.loadAll();
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
            java.awt.EventQueue.invokeLater(ManagementView::new);
        } else {
            logger.info("Starting login window");
            if(LoginWindowController.shouldSkipAuth()){
                java.awt.EventQueue.invokeLater(ManagementView::new);
            } else {
                java.awt.EventQueue.invokeLater(LoginWindowView::new);
            }
        }

    }
}
