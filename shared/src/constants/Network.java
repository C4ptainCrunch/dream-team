package constants;


import java.util.ArrayList;

public final class Network {

    public static final class HOST{
        public static final String HOSTNAME = "http://localhost";
        public static final int PORT = 5555 ;
        public static final String COMPLETE_HOSTNAME = HOSTNAME + ":" + Integer.toString(PORT);
    }

    public static final class Login{
        public static final String LOGIN_OK = "login_ok";
        public static final String ACCOUNT_NOT_ACTIVATED = "acc_n_activated";
        public static final String LOGIN_FAILED = "login_nok";
    }

    public static final class Token{
        public static final String TOKEN_OK = "token_ok";
    }

    public static final class Signup{
        public static final String SIGN_UP_OK = "signup_ok";
        public static final String SIGN_UP_FAILED = "signup_failed";
        public static final ArrayList<String> FIELDS_NAMES = new ArrayList<String>(){{
            add("firstname");
            add("lastname");
            add("username");
            add("email");
        }};
    }

}
