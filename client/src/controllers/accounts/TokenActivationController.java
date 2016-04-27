package controllers.accounts;

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

    public TokenActivationController(TokenActivationView view) {
        this.view = view;
    }

    /**
     * Send the entered token to the server
     * @param token The entered token
     * @param username The username
     */

    public void validateToken(String token, String username) {

        Client client = ClientBuilder.newClient();

        Form postForm = new Form("token",token);

        Response response = client.target("http://localhost:5555")
                .path("user/activate/"+username)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.form(postForm));

        if(response.readEntity(String.class).equals("OK")){
            this.view.dispose();
            this.view.correctTokenDialog();
        } else {
            this.view.wrongTokenWarning();
        }

    }
}
