import database.DAOFactory;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import ressources.UserRessource;
import utils.Log;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        DAOFactory daoFactory = DAOFactory.getInstance();

        URI baseUri = UriBuilder.fromUri("http://localhost/").port(5555).build();
        ResourceConfig config = new ResourceConfig(UserRessource.class);
        JdkHttpServerFactory.createHttpServer(baseUri, config);

        logger.info("Server started on http://localhost:5555/");
    }

}
