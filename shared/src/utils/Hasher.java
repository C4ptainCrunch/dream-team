package utils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hasher {
    private static final int ITERATIONS = 1000;
    private static final int KEY_LENGTH = 192; // bits

    public static String hash(String password, String salt){
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(
                passwordChars,
                saltBytes,
                ITERATIONS,
                KEY_LENGTH
        );

        try {
            SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashedPassword = key.generateSecret(spec).getEncoded();
            return String.format("$p5k2$%x$%s$%x", ITERATIONS, salt, new BigInteger(hashedPassword));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e){
            throw new RuntimeException(e);
        }

    }

    public static boolean isEqual(String hash, String password) {
        if(!hash.startsWith("$p5k2$")){
            throw new IllegalArgumentException("Hash was not a PBKDF2WithHmacSHA1");
        }

        if(!hash.startsWith("$p5k2$3e8$")){
            throw new IllegalArgumentException("Hash has a wrong number of rounds");
        }

        String salt = extractSalt(hash);
        return hash(password, salt).equals(hash);
    }

    public static String skipStatic(String hash){
        return hash.substring("$p5k2$3e8$".length());
    }

    public static String extractSalt(String hash){
        hash = skipStatic(hash);
        int stop = hash.indexOf('$');
        return hash.substring(0, stop);
    }

    public static String extractHmac(String hash){
        hash = skipStatic(hash);
        int start = hash.indexOf('$') + 1;
        return hash.substring(start);
    }

}
