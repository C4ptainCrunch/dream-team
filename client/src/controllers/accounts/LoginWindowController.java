package controllers.accounts;

import views.accounts.LoginWindowView;
import views.accounts.SignUpView;

/**
 * Created by bambalaam on 21/04/16.
 */
public class LoginWindowController {

    private final LoginWindowView view;

    public LoginWindowController(LoginWindowView view) {
        this.view = view;
    }

    public void login() {

    }

    public void signUp() {
        new SignUpView(this.view);
    }
}
