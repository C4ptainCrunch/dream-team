package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class Log {
    public static Logger getLogger(Class c) {
        return getLogger(c.getName());
    }

    public static Logger getLogger(String name) {
        return getLogger(name, Level.ALL);
    }

    public static Logger getLogger(String name, Level level) {
        Logger l = Logger.getLogger(name);
        l.setLevel(level);
        return l;
    }

    public static void init() {
        Handler ch = new ConsoleHandler();
        ch.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord logRecord) {
                Date date = new Date(logRecord.getMillis());
                return String.format("%s %s (%s:%s) : %s\n", new SimpleDateFormat("H:m:s").format(date), logRecord.getLevel(),
                        logRecord.getLoggerName(), logRecord.getSourceMethodName(), logRecord.getMessage());
            }
        });
        ch.setLevel(Level.ALL);
        for (Handler h : Logger.getLogger("").getHandlers()) {
            Logger.getLogger("").removeHandler(h);
        }

        Logger.getLogger("").addHandler(ch);
    }
}
