
import database.DAOFactory;
import utils.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Logger.getLogger("com.sun.jersey.api.core").setLevel(Level.WARNING);
        logger.info("Starting the server on http://localhost:5555");
        DAOFactory daoFactory = DAOFactory.getInstance();
        SimpleServerFactory.create("http://localhost:5555");
    }

}
