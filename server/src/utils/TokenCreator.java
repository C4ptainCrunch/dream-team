package utils;

import constants.Tokens;

import java.security.SecureRandom;

/**
 * Created by bambalaam on 25/04/16.
 */
public class TokenCreator {

    public TokenCreator(){

    }

    public String newToken(){
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
