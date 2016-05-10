
import java.io.IOException;
import java.util.logging.Logger;

import models.project.Diagram;
import utils.Log;
import views.accounts.LoginWindowView;
import views.editor.EditorView;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);

    public static void main(String... args) {
        Log.init();
        if(args.length > 0 && args[0].equals("editor")){
            logger.info("Skip to the editor");
            java.awt.EventQueue.invokeLater(() -> {
                try {
                    new EditorView(new Diagram());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            logger.info("Starting project management view");
            java.awt.EventQueue.invokeLater(LoginWindowView::new);
        }

    }
}
