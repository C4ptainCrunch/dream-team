package constants;


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

}
