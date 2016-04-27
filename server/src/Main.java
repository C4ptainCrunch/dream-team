import java.net.URI;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;

import constants.Network;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import ressources.UserRessource;
import utils.Log;
import database.DAOFactory;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        DAOFactory daoFactory = DAOFactory.getInstance();

        URI baseUri = UriBuilder.fromUri(Network.HOST.HOSTNAME+"/").port(Network.HOST.PORT).build();
        ResourceConfig config = new ResourceConfig(UserRessource.class);
        JdkHttpServerFactory.createHttpServer(baseUri, config);

        logger.info("Server started on" + Network.HOST.COMPLETE_HOSTNAME);
    }

}
