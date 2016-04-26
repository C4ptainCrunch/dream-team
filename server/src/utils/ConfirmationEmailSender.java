package utils;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by bambalaam on 26/04/16.
 */
public class ConfirmationEmailSender {

    public ConfirmationEmailSender(String recipient, String token) {
        String to = recipient;
        String from = "donotreply@creatikz.com";
        String host = "localhost";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(props);

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("This is the Subject Line!");
            message.setText("Dear CreaTikZ user. Here is your token: "+token+
                            "\nPlease input it on the window that opened after you signed up." +
                            "\nIf you closed the program in the meantime, relaunch the program" +
                            " and click on 'Token Activation'");
            Transport.send(message);
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
