package com.lucas.ticketsalesmanager.models.paymentMethod;

public class TED extends Payment {
    private String accountNumber;
    private String agentNumber;

    public TED(String accountNumber, String agentNumber) {
        this.accountNumber = accountNumber;
        this.agentNumber = agentNumber;
    }

    // Getters e Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAgentNumber() {
        return agentNumber;
    }

    public void setAgentNumber(String agentNumber) {
        this.agentNumber = agentNumber;
    }

    // Methods overriding
    @Override
    public boolean validate(String paymentDetails) {
        return paymentDetails.equals(accountNumber + "-" + agentNumber);
    }

    @Override
    public boolean executePayment() {
        System.out.println("Processing TED payment...");
        return true;
    }
}
