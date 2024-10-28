package com.lucas.ticketsalesmanager.models.paymentMethod;

import com.lucas.ticketsalesmanager.models.Ticket;

public class PhysicalMoney extends Payment {
    private double amount;
    private Ticket ticket;

    public PhysicalMoney(double amount, Ticket ticket) {
        this.amount = amount;
        this.ticket = ticket;
    }

    // Getters e Setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    // Methods overriding
    @Override
    public boolean validate(String paymentDetails) {
        try {
            double providedAmount = Double.parseDouble(paymentDetails);
            return providedAmount >= ticket.getPrice();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean executePayment() {
        System.out.println("Processing cash payment...");
        return true;
    }
}
