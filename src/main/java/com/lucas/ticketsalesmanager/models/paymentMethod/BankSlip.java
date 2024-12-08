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
 * The BankSlip class represents a payment method using a bank slip.
 * It contains details such as the account number and agent number associated with the payment.
 */
public class BankSlip extends Payment {
    private String accountNumber;
    private String agentNumber;

    /**
     * Constructs a new BankSlip object with the specified account and agent numbers.
     *
     * @param accountNumber The account number for the bank slip.
     * @param agentNumber The agent number for the bank slip.
     */
    public BankSlip(String accountNumber, String agentNumber) {
        this.accountNumber = accountNumber;
        this.agentNumber = agentNumber;
    }

    // Getters and Setters

    /**
     * Gets the account number.
     *
     * @return The account number.
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the account number.
     *
     * @param accountNumber The account number to set.
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Gets the agent number.
     *
     * @return The agent number.
     */
    public String getAgentNumber() {
        return agentNumber;
    }

    /**
     * Sets the agent number.
     *
     * @param agentNumber The agent number to set.
     */
    public void setAgentNumber(String agentNumber) {
        this.agentNumber = agentNumber;
    }

    // Methods overriding

    /**
     * Validates the payment details for the bank slip.
     *
     * @param paymentDetails The details to validate (should be in the format "accountNumber-agentNumber").
     * @return {@code true} if the payment details match the account and agent numbers, {@code false} otherwise.
     */
    @Override
    public boolean validate(String paymentDetails) {
        return true;
    }

    /**
     * Executes the payment process for the bank slip.
     *
     * @return {@code true} if the payment was successful, {@code false} otherwise.
     */
    @Override
    public boolean executePayment() {
        System.out.println("Processing bank slip payment...");
        return true;
    }
}
