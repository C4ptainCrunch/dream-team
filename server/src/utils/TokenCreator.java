package utils;

import java.security.SecureRandom;

import constants.Tokens;

/**
 * Implementation of a Token Creator used for the email confirmation.
 */
public class TokenCreator {
    private TokenCreator() {
         // Static class
    }

    /**
     * Create and returns a new Token.
     * @return the created Token
     */
    public static String newToken(){
        char[] chars = Tokens.TOKEN_ALPHABET.toCharArray();
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 32; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }
}
