package misc.utils;

import constants.Network;
import utils.Dirs;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Form;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class RequestBuilder {
    private static String token = getDiskToken();

    public static Invocation get(String path) {
        return ClientBuilder.newClient()
                .target(Network.HOST.COMPLETE_HOSTNAME).path(path)
                .request().header("Authorization", "Bearer " + token)
                .buildGet();
    }

    public static Invocation post(String path, Form form) {
        return post(path, Entity.form(form));
    }

    public static Invocation post(String path, Entity entity) {
        return ClientBuilder.newClient()
                .target(Network.HOST.COMPLETE_HOSTNAME).path(path)
                .request().header("Authorization", "Bearer " + token)
                .buildPost(entity);
    }


    private static String getDiskToken() {
        try {
            return new String(Files.readAllBytes(getTokenPath())).trim();
        } catch (IOException e) {
            return "";
        }
    }

    public static void setToken(String token) {
        RequestBuilder.token = token;
        try {
            Files.write(getTokenPath(), token.trim().getBytes());
        } catch (IOException e) {
        }
    }

    public static Path getTokenPath() {
        return Dirs.getDataDir().resolve(Paths.get("token"));
    }
}
