package controllers.accounts;

import views.accounts.TokenActivationView;

/**
 * Created by bambalaam on 26/04/16.
 */
public class TokenActivationController {

    private TokenActivationView view;

    public TokenActivationController(TokenActivationView view) {
        this.view = view;
    }

    public void validateToken(String token, String username) {

        String tokenServer = "TEST"; // GET TOKEN FROM SERVER

        /* Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:5555").path("resource");

        Form form = new Form();
        form.param("x", "foo");
        form.param("y", "bar");

        MyJAXBBean bean = target.request(
                MediaType.APPLICATION_JSON_TYPE).post(
                    Entity.entity(
                            form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                            MyJAXBBean.class); */

        if(token.equals(tokenServer)){
            this.view.dispose();
            this.view.correctTokenDialog();
        } else {
            this.view.wrongTokenWarning();
        }

    }
}
