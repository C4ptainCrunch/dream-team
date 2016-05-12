import java.net.URI;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;

import database.CreateDatabase;
import middleware.AuthenticationFilter;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import utils.Log;
import constants.Network;
import constants.ServerPropertiesLoader;
import endpoints.AuthenticationEndpoint;
import endpoints.UserEndpoint;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Log.init();
        ServerPropertiesLoader.loadAll();
        CreateDatabase.createDatabaseIfDoesntExists();
        URI baseUri = UriBuilder.fromUri(Network.HOST.HOSTNAME+"/").port(Network.HOST.PORT).build();
        ResourceConfig config = new ResourceConfig(
                UserEndpoint.class,
                AuthenticationEndpoint.class,
                AuthenticationFilter.class
        );
        JdkHttpServerFactory.createHttpServer(baseUri, config);

        logger.info("Server started on" + Network.HOST.COMPLETE_HOSTNAME);
    }

}
