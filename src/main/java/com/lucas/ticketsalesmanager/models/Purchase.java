package com.lucas.ticketsalesmanager.models;

import com.lucas.ticketsalesmanager.models.paymentMethod.Payment;
import com.lucas.ticketsalesmanager.service.communication.PurchaseConfirmation;


public class Purchase {
    // Attributes
    private User user;
    private Ticket ticket;
    private Payment paymentMethod;
    private PurchaseConfirmation confirmation;

    // Constructor
    public Purchase(User user, Ticket ticket, Payment paymentMethod) {
        this.user = user;
        this.ticket = ticket;
        this.paymentMethod = paymentMethod;
        this.confirmation = new PurchaseConfirmation();
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Payment getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Overridden Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;
        return user.equals(purchase.user) && ticket.equals(purchase.ticket) && paymentMethod.equals(purchase.paymentMethod);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + ticket.hashCode();
        result = 31 * result + paymentMethod.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "user=" + user +
                ", ticket=" + ticket +
                ", payment method=" + paymentMethod +
                '}';
    }

    // Methods
    public boolean processPurchase(User user, Ticket ticket, Payment paymentMethod) {
        boolean paymentStatus = paymentMethod.pay(Float.toString(ticket.getPrice()));
        if (paymentStatus) {
            PurchaseConfirmation.sendEmail(user, toString());
            return true;
        }
        return false;
    }
}
