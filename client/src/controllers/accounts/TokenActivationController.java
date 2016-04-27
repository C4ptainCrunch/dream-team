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
 * Created by bambalaam on 26/04/16.
 */
public class TokenActivationController {

    private TokenActivationView view;

    public TokenActivationController(TokenActivationView view) {
        this.view = view;
    }

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
