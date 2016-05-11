package constants;


import java.util.ArrayList;

public final class Network {

    public static final class HOST{
        public static String HOSTNAME = "http://localhost";
        public static int PORT = 5555;
        public static String COMPLETE_HOSTNAME = HOSTNAME + ":" + Integer.toString(PORT);
    }

    public static final class Login{
        public static String LOGIN_OK = "login_ok";
        public static String ACCOUNT_NOT_ACTIVATED = "acc_n_activated";
        public static String LOGIN_FAILED = "login_nok";
    }

    public static final class Token{
        public static String TOKEN_OK = "token_ok";
    }

    public static final class Signup{
        public static String SIGN_UP_OK = "signup_ok";
        public static String SIGN_UP_FAILED = "signup_failed";
        public static ArrayList<String> FIELDS_NAMES = new ArrayList<String>(){{
            add("firstname");
            add("lastname");
            add("username");
            add("email");
        }};
    }

}
