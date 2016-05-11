package misc.utils;

import constants.Network;
import utils.Dirs;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RequestBuilder {
    private static String token = getDiskToken();

    public static Invocation get(String path) {
        return ClientBuilder.newClient()
                .target(Network.HOST.COMPLETE_HOSTNAME).path("/authentication/checktoken")
                .request().header("Authorization", "Bearer " + token)
                .buildGet();
    }

    private static String getDiskToken() {
        Path tokenPath = Dirs.getDataDir().resolve(Paths.get("token"));
        try {
            return new String(Files.readAllBytes(tokenPath)).trim();
        } catch (IOException e) {
            return "";
        }
    }

}
