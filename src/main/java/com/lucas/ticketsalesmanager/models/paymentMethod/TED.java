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

/**
 * The TED class represents a bank transfer payment method (Transferência Eletrônica Disponível).
 * It extends the Payment class and provides functionality specific to TED transactions.
 */
public class TED extends Payment {
    private String accountNumber;
    private String agentNumber;

    /**
     * Constructs a new TED payment method with the specified account and agent numbers.
     *
     * @param accountNumber The account number for the TED payment.
     * @param agentNumber   The agent number for the TED payment.
     */
    public TED(String accountNumber, String agentNumber) {
        this.accountNumber = accountNumber;
        this.agentNumber = agentNumber;
    }

    // Getters and Setters
    /**
     * Gets the account number associated with the TED payment.
     *
     * @return The account number.
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the account number for the TED payment.
     *
     * @param accountNumber The new account number.
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Gets the agent number associated with the TED payment.
     *
     * @return The agent number.
     */
    public String getAgentNumber() {
        return agentNumber;
    }

    /**
     * Sets the agent number for the TED payment.
     *
     * @param agentNumber The new agent number.
     */
    public void setAgentNumber(String agentNumber) {
        this.agentNumber = agentNumber;
    }

    // Methods overriding
    /**
     * Validates the payment details for the TED payment.
     *
     * @param paymentDetails The details to validate, formatted as "accountNumber-agentNumber".
     * @return {@code true} if the payment details are valid, {@code false} otherwise.
     */
    @Override
    public boolean validate(String paymentDetails) {
        return true;
    }

    /**
     * Executes the TED payment process.
     *
     * @return {@code true} if the payment was processed successfully, {@code false} otherwise.
     */
    @Override
    public boolean executePayment() {
        System.out.println("Processing TED payment...");
        return true; // Simulate successful payment processing
    }
}
