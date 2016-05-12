package controllers.accounts;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import misc.utils.RequestBuilder;
import models.NetworkRequest;
import views.accounts.LoginWindowView;
import views.accounts.TokenActivationView;
import constants.Network;

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
        Response r = RequestBuilder.post("/user/activate/" + username, postForm).invoke();
        String resp = r.readEntity(String.class);

        if(resp.equals(Network.Token.TOKEN_OK)){
            this.view.dispose();
            this.view.correctTokenDialog();
            java.awt.EventQueue.invokeLater(LoginWindowView::new);
        } else {
            this.view.wrongTokenWarning();
        }

    }
}
