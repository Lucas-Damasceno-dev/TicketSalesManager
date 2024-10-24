package com.lucas.ticketsalesmanager.service.communication;

import com.lucas.ticketsalesmanager.models.User;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class PurchaseConfirmation {

    // Internal Settings
    private static final String SENDER_EMAIL = "your_email@gmail.com";
    private static final String SENDER_PASSWORD = "your_password_here";

    public static void sendEmail(User user, String content) {
        // SMTP server settings (example with Gmail)
        String host = "smtp.gmail.com";
        String to = user.getEmail();


        // SMTP server settings
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create an authenticated session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            // Compose the email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Purchase Confirmation");
            message.setText(content);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}