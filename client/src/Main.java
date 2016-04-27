
import java.util.logging.Logger;
import utils.Log;
import views.accounts.LoginWindowView;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);

    public static void main(String... args) {
        Log.init();
        logger.info("Starting project management view");

        java.awt.EventQueue.invokeLater(LoginWindowView::new);

    }
}
