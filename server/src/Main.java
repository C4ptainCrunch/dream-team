import com.sun.jersey.simple.container.SimpleServerFactory;
import database.DAOFactory;
import utils.ConfirmationEmailSender;
import utils.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Logger.getLogger("com.sun.jersey.api.core").setLevel(Level.WARNING);
        logger.info("Starting the server on http://localhost:5555");
        TestDAO t = new TestDAO();
        t.test();
        SimpleServerFactory.create("http://localhost:5555");

        ConfirmationEmailSender mail = new ConfirmationEmailSender("andre.mm.90@gmail.com","TESTTESTTEST");
    }

}
