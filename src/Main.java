import gui.projectManagement.views.ProjectManagementView;
import utils.Log;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);

    public static void main(String... args) {
        Handler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        Logger.getLogger("").addHandler(ch);

        logger.info("Starting project management view");

        java.awt.EventQueue.invokeLater(ProjectManagementView::new);

    }
}
