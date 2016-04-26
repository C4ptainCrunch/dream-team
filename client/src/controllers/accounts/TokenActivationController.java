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

    public void validateToken(String token) {

        Boolean serverValidation = true; // // FETCH CORRECT TOKEN IN SERVER

        if(serverValidation){
            this.view.dispose();
            this.view.correctTokenDialog();
        } else {
            this.view.wrongTokenWarning();
        }

    }
}
