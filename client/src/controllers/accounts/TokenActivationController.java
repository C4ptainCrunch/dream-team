package controllers.accounts;

import constants.Network;
import misc.utils.RequestBuilder;
import views.accounts.LoginWindowView;
import views.accounts.TokenActivationView;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

/**
 * The controller related to the TokenActivationView.
 */

public class TokenActivationController {

    private TokenActivationView view;
    private final String BASE_PATH = "user/activate/";

    public TokenActivationController(final TokenActivationView view) {
        this.view = view;
    }

    /**
     * Send the entered token to the server
     * @param token The entered token
     * @param username The username
     */

    public void validateToken(final String token, final String username) {
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

    public void cancel() {
        this.view.dispose();
        java.awt.EventQueue.invokeLater(LoginWindowView::new);
    }
}
