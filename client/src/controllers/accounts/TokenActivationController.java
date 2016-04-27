package controllers.accounts;

import constants.Network;
import models.NetworkRequest;
import views.accounts.LoginWindowView;
import views.accounts.TokenActivationView;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The controller related to the TokenActivationView.
 */

public class TokenActivationController {

    private TokenActivationView view;
    private final String BASE_PATH = "user/activate/";

    public TokenActivationController(TokenActivationView view) {
        this.view = view;
    }

    /**
     * Send the entered token to the server
     * @param token The entered token
     * @param username The username
     */

    public void validateToken(String token, String username) {

        Form postForm = new Form("token",token);
        NetworkRequest request = new NetworkRequest(Network.HOST.COMPLETE_HOSTNAME, BASE_PATH+username, MediaType.TEXT_PLAIN_TYPE);
        request.post(postForm);

        String response = request.getResponseAsString();

        if(response.equals(Network.Token.TOKEN_OK)){
            this.view.dispose();
            this.view.correctTokenDialog();
            new LoginWindowView();
        } else {
            this.view.wrongTokenWarning();
        }

    }
}
