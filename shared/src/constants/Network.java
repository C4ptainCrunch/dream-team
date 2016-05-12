package constants;

import java.util.List;

public final class Network {

    public static final class HOST {
        public static String HOSTNAME;
        public static int PORT;
        public static String COMPLETE_HOSTNAME;
    }

    public static final class Login {
        public static String LOGIN_OK;
        public static String ACCOUNT_NOT_ACTIVATED;
        public static String LOGIN_FAILED;
    }

    public static final class Token {
        public static String TOKEN_OK;
    }

    public static final class Signup {
        public static String SIGN_UP_OK;
        public static String SIGN_UP_FAILED;
        public static List<String> FIELDS_NAMES;
    }

}
