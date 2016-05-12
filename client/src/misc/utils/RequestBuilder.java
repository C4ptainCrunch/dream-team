package misc.utils;

import constants.Network;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import utils.Dirs;
import utils.Log;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class RequestBuilder {
    private static String token = getDiskToken();
    private static final Logger logger = Log.getLogger(RequestBuilder.class);

    public static Invocation get(final String path) {
        return getClient().path(path).request()
                .header("Authorization", "Bearer " + token)
                .buildGet();
    }

    public static Invocation post(final String path, final Form form) {
        return post(path, Entity.form(form));
    }

    public static Invocation post(final String path, final Entity entity) {
        return getClient().path(path).request()
                .header("Authorization", "Bearer " + token)
                .buildPost(entity);
    }

    public static Invocation put(final String path, final InputStream stream, final String type) {
        return  getClient().path(path).request()
                .header("Authorization", "Bearer " + token)
                .buildPut(Entity.entity(stream, type));
    }

    private static WebTarget getClient(){
        return  ClientBuilder.newClient()
                .target(Network.HOST.COMPLETE_HOSTNAME)
                .register(MultiPartFeature.class);
    }

    private static String getDiskToken() {
        try {
            return new String(Files.readAllBytes(getTokenPath())).trim();
        } catch (IOException e) {
            return "";
        }
    }

    public static void setToken(final String token) {
        RequestBuilder.token = token;
        try {
            Files.write(getTokenPath(), token.trim().getBytes());
        } catch (IOException e) {
            logger.fine("Failed to write set token in file");
        }
    }

    public static Path getTokenPath() {
        return Dirs.getDataDir().resolve(Paths.get("token"));
    }
}
