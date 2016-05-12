import java.net.URI;
import java.util.logging.Logger;

import javax.ws.rs.core.UriBuilder;

import endpoints.AuthenticationEndpoint;
import endpoints.ProjectEndpoint;
import middleware.AuthenticationFilter;
import constants.Network;
import constants.ServerPropertiesLoader;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


import endpoints.UserEndpoint;
import utils.Log;
import database.DAOFactory;

public class Main {
    private static final Logger logger = Log.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Log.init();
        ServerPropertiesLoader.loadAll();

        URI baseUri = UriBuilder.fromUri(Network.HOST.HOSTNAME+"/").port(Network.HOST.PORT).build();
        ResourceConfig config = new ResourceConfig(
                UserEndpoint.class,
                ProjectEndpoint.class,
                AuthenticationEndpoint.class,
                AuthenticationFilter.class
        );
        JdkHttpServerFactory.createHttpServer(baseUri, config);

        logger.info("Server started on" + Network.HOST.COMPLETE_HOSTNAME);
    }

}
