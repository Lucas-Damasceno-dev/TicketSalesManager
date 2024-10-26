package com.lucas.ticketsalesmanager.service.communication;

import com.lucas.ticketsalesmanager.models.User;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class PurchaseConfirmation {

    // Internal Settings
    private static final String SENDER_EMAIL = System.getenv("SENDER_EMAIL");
    private static final String SENDER_PASSWORD = System.getenv("SENDER_PASSWORD");
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    public static void sendEmail(User user, String content) throws MessagingException {
        String to = user.getEmail();

        // SMTP server settings
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create an authenticated session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        // Compose the email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Purchase Confirmation");
        message.setContent(content, "text/html; charset=utf-8");

        // Send the email
        Transport.send(message);
        System.out.println("Email sent successfully!");
    }
}
