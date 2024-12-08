/***********************************************************************************************
Author: LUCAS DA CONCEIÇÃO DAMASCENO
Curricular Component: EXA 863 - MI Programming - 2024.2 - TP01
Completed on: 10/24/2024
I declare that this code was prepared by me individually and does not contain any
code snippet from another colleague or another author, such as from books and
handouts, and web pages or electronic documents. Any piece of code
by someone other than mine is highlighted with a citation for the author and source
of the code, and I am aware that these excerpts will not be considered for evaluation purposes
************************************************************************************************/

package com.lucas.ticketsalesmanager.service.communication;

import com.lucas.ticketsalesmanager.models.User;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

/**
 * The PurchaseConfirmation class is responsible for sending purchase confirmation emails to users.
 */
public class PurchaseConfirmation {

    private static final String SENDER_EMAIL = System.getenv("SENDER_EMAIL");
    private static final String SENDER_PASSWORD = System.getenv("SENDER_PASSWORD");
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    /**
     * Sends a purchase confirmation email to the specified user.
     *
     * @param user    The user to whom the confirmation email will be sent.
     * @param content The content of the email, typically containing purchase details.
     * @throws MessagingException If there is a problem with the email sending process.
     */
    public static void sendEmail(User user, String content) throws MessagingException {
        String to = user.getEmail();

        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject("Purchase Confirmation");
        message.setContent(content, "text/html; charset=utf-8");

        Transport.send(message);
        System.out.println("Email sent successfully!");
    }
}
