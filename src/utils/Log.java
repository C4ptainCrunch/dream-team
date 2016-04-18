package utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
    public static Logger getLogger(Class c){
        return getLogger(c.getName());
    }

    public static Logger getLogger(String name){
        return getLogger(name, Level.ALL);
    }

    public static Logger getLogger(String name, Level level){
        Logger l = Logger.getLogger(name);
        l.setLevel(level);
        return l;
    }
}
