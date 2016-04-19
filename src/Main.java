import gui.projectManagement.views.ProjectManagementView;
import utils.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);

    public static void main(String... args) {
        Handler ch = new ConsoleHandler();
        ch.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord logRecord) {
                Date date = new Date(logRecord.getMillis());
                return String.format(
                        "%s %s (%s:%s) : %s\n",
                        new SimpleDateFormat("H:m:s").format(date),
                        logRecord.getLevel(),
                        logRecord.getLoggerName(),
                        logRecord.getSourceMethodName(),
                        logRecord.getMessage()
                );
            }
        });
        ch.setLevel(Level.ALL);
        for (Handler h: Logger.getLogger("").getHandlers()) {
            Logger.getLogger("").removeHandler(h);
        }

        Logger.getLogger("").addHandler(ch);

        logger.info("Starting project management view");

        java.awt.EventQueue.invokeLater(ProjectManagementView::new);

    }
}
