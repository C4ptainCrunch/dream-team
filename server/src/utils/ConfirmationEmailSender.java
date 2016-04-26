package utils;

import constants.Email;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by bambalaam on 26/04/16.
 */
public class ConfirmationEmailSender {

    public ConfirmationEmailSender(String recipient, String token) {
        String to = recipient;
        String from = "donotreply@creatikz.com";
        String host = "127.0.0.1";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "5555");

        Session session = Session.getInstance(props,null);

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(Email.subjectLine);
            message.setText(Email.emailBodyPartOne+token+Email.emailBodyPartTwo);

            Transport transport = session.getTransport();
            transport.connect();
            transport.sendMessage(message,
                    message.getRecipients(javax.mail.Message.RecipientType.TO));
            transport.close();

            System.out.println("Got here!");
            //Transport.send(message);
            System.out.println("Email sent");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
