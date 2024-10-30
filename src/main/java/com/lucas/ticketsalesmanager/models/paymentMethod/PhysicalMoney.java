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

package com.lucas.ticketsalesmanager.models.paymentMethod;

import com.lucas.ticketsalesmanager.models.Ticket;

/**
 * The PhysicalMoney class represents a payment method using physical cash.
 * It extends the Payment class and provides functionality specific to cash transactions.
 */
public class PhysicalMoney extends Payment {
    private double amount;
    private Ticket ticket;

    /**
     * Constructs a new PhysicalMoney payment with the specified amount and ticket.
     *
     * @param amount The amount of cash for the payment.
     * @param ticket The ticket associated with the payment.
     */
    public PhysicalMoney(double amount, Ticket ticket) {
        this.amount = amount;
        this.ticket = ticket;
    }

    // Getters and Setters
    /**
     * Gets the amount of cash for the payment.
     *
     * @return The amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of cash for the payment.
     *
     * @param amount The new amount.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the ticket associated with the payment.
     *
     * @return The ticket.
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Sets the ticket associated with the payment.
     *
     * @param ticket The new ticket.
     */
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    // Methods overriding
    /**
     * Validates the payment details for the cash payment.
     *
     * @param paymentDetails The details to validate, typically the provided cash amount as a string.
     * @return {@code true} if the provided amount is sufficient for the ticket price, {@code false} otherwise.
     */
    @Override
    public boolean validate(String paymentDetails) {
        try {
            double providedAmount = Double.parseDouble(paymentDetails);
            return providedAmount >= ticket.getPrice();
        } catch (NumberFormatException e) {
            return false; // Invalid input, return false
        }
    }

    /**
     * Executes the cash payment process.
     *
     * @return {@code true} if the payment was processed successfully, {@code false} otherwise.
     */
    @Override
    public boolean executePayment() {
        System.out.println("Processing cash payment...");
        return true; // Simulate successful payment processing
    }
}
