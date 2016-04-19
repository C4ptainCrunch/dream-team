import gui.projectManagement.views.ProjectManagementView;
import utils.Log;

import java.util.logging.*;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);

    public static void main(String... args) {
        Log.init();
        logger.info("Starting project management view");

        java.awt.EventQueue.invokeLater(ProjectManagementView::new);

    }
}
