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

package com.lucas.ticketsalesmanager.models;

import com.lucas.ticketsalesmanager.models.paymentMethod.Payment;
import com.lucas.ticketsalesmanager.service.communication.PurchaseConfirmation;
import jakarta.mail.MessagingException;

import java.util.Date;
import java.util.Objects;

/**
 * The Purchase class represents a ticket purchase made by a user.
 * It contains information about the user, ticket, payment method, confirmation, and purchase date.
 */
public class Purchase {
    // Attributes
    private User user;
    private Ticket ticket;
    private Payment paymentMethod;
    private final PurchaseConfirmation confirmation;
    private final Date date;

    // Constructor
    /**
     * Constructs a new Purchase with the provided user, ticket, and payment method.
     * It initializes the confirmation and purchase date.
     *
     * @param user The user making the purchase.
     * @param ticket The ticket being purchased.
     * @param paymentMethod The payment method used for the purchase.
     */
    public Purchase(User user, Ticket ticket, Payment paymentMethod) {
        this.user = user;
        this.ticket = ticket;
        this.paymentMethod = paymentMethod;
        this.confirmation = new PurchaseConfirmation();
        this.date = new Date();
    }

    // Getters and Setters
    /**
     * Gets the user who made the purchase.
     *
     * @return The user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who made the purchase.
     *
     * @param user The user to set.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the ticket associated with the purchase.
     *
     * @return The ticket.
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Sets the ticket for the purchase.
     *
     * @param ticket The ticket to set.
     */
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * Gets the payment method used for the purchase.
     *
     * @return The payment method.
     */
    public Payment getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the payment method for the purchase.
     *
     * @param paymentMethod The payment method to set.
     */
    public void setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Gets the date of the purchase.
     *
     * @return The purchase date.
     */
    public Date getPurchaseDate() {
        return date;
    }

    // Overridden Methods
    /**
     * Compares two Purchase objects for equality based on their attributes.
     *
     * @param o The object to be compared.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(user, purchase.user) &&
                Objects.equals(ticket, purchase.ticket) &&
                Objects.equals(paymentMethod, purchase.paymentMethod) &&
                Objects.equals(confirmation, purchase.confirmation) &&
                Objects.equals(date, purchase.date);
    }

    /**
     * Returns the hash code for the Purchase object based on user, ticket, and payment method.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(user, ticket, paymentMethod, confirmation, date);
    }

    /**
     * Returns a string representation of the Purchase object.
     *
     * @return A string containing purchase details.
     */
    @Override
    public String toString() {
        return "Purchase{" +
                "user=" + user +
                ", ticket=" + ticket +
                ", paymentMethod=" + paymentMethod +
                ", confirmation=" + confirmation +
                ", date=" + date +
                '}';
    }

    // Methods
    /**
     * Processes the purchase by verifying payment and sending a confirmation email.
     *
     * @param user The user making the purchase.
     * @param ticket The ticket being purchased.
     * @param paymentMethod The payment method used.
     * @return {@code true} if the purchase is successful, {@code false} otherwise.
     * @throws MessagingException if there is an error sending the confirmation email.
     */
    public boolean processPurchase(User user, Ticket ticket, Payment paymentMethod) throws MessagingException {
        boolean paymentStatus = paymentMethod.processPayment(Float.toString(ticket.getPrice()));
        if (paymentStatus) {
            try {
                PurchaseConfirmation.sendEmail(user, this.toString());
                return true;
            } catch (Exception e) {
                // Log the error or handle it as needed
                return true; // Consider modifying this to indicate email failure
            }
        }
        return false;
    }
}
