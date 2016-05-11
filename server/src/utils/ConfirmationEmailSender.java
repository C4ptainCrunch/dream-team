package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import constants.Email;


public class ConfirmationEmailSender {
    private final static Logger logger = Log.getLogger(ConfirmationEmailSender.class);

    public static void send(String recipient, String token) throws MessagingException {
        try {
            Properties config = new Properties();
            config.load(new FileInputStream("config.ini"));

            String from = (String) config.get("email_from");
            if(from == null){
                logger.severe("email_from not present in the config.ini file");
            }
            String smtpUsername = (String) config.get("smtp_username");
            if(smtpUsername == null){
                logger.severe("smtp_username not present in the config.ini file");
            }
            String smtpPassword = (String) config.get("smtp_password");
            if(smtpPassword == null){
                logger.severe("smtp_password not present in the config.ini file");
            }
            String smtpHost = (String) config.get("smtp_host");
            if(smtpHost == null){
                logger.severe("smtp_host not present in the config.ini file");
            }

            if(from == null || smtpUsername == null || smtpPassword == null || smtpHost == null){
                logger.severe("fallback to email logger mock");
                throw new IOException();
            }

            Properties props = new Properties();

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", smtpHost);
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(smtpUsername, smtpPassword);
                        }
                    });


            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(
                    RecipientType.TO,
                    new InternetAddress(recipient)
            );
            message.setSubject(Email.SUBJECT_LINE);
            message.setText(Email.EMAIL_BODY_PART_ONE + token + Email.EMAIL_BODY_PART_TWO);

            Transport.send(message);
        } catch (IOException e) {
            logger.info("No email configured. Mocking email to " + recipient + " : " + token);
        }


    }
}
