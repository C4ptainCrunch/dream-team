package controllers.accounts;

import views.accounts.LoginWindowView;
import views.accounts.SignUpView;
import views.accounts.TokenActivationView;
import views.management.ProjectManagementView;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The controller related to the LoginWindowView.
 */

public class LoginWindowController {

    private final LoginWindowView view;

    public LoginWindowController(LoginWindowView view) {
        this.view = view;
    }

    /**
     * Launch the login process.
     * @param username The user's username
     * @param password The user's password
     */

    public void login(String username, String password) {
        Client client = ClientBuilder.newClient();

        Form postForm = new Form();
        postForm.param("username", username);
        postForm.param("password", password);

        Response response = client.target("http://localhost:5555")
                .path("user/login/"+username)
                .request(MediaType.TEXT_PLAIN_TYPE)
                .post(Entity.form(postForm));

        if(response.readEntity(String.class).equals("OK")){
            this.view.dispose();
            new ProjectManagementView();
        } else {
            System.out.println(response.readEntity(String.class));
        }
    }

    /**
     * Launch the sign up process.
     */

    public void signUp() {
        new SignUpView(this.view);
    }

    /**
     * Launch the token activation process.
     */

    public void tokenActivation() {
        new TokenActivationView();
    }
}
