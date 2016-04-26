package utils;

import constants.Email;
import constants.EmailPassword;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;

/**
 * Created by bambalaam on 26/04/16.
 */
public class ConfirmationEmailSender {

    public ConfirmationEmailSender(String recipient, String token) {
        String to = recipient;
        String from = "creatikz.one@gmail.com";
        final String username = "creatikz.one";
        final String password = EmailPassword.PASSWORD;
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(RecipientType.TO,
                                new InternetAddress(to));
            message.setSubject(Email.subjectLine);
            message.setText(Email.emailBodyPartOne+token+Email.emailBodyPartTwo);

            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
