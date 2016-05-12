package utils;

import org.junit.Test;

/**
 * Created by nikita on 4/27/16.
 */
public class ConfirmationEmailSenderTest extends ServerTest {
    @Test
    public void send() throws Exception {
        ConfirmationEmailSender.send("plop@gmail.com", "my_super_secret_token");
    }

}
