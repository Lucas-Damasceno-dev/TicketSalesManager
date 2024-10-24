package com.lucas.ticketsalesmanager.models.paymentMethod;

import java.util.Objects;

public class TED extends Payment{
    // Attributes
    private String accountNumber;
    private String agentNumber;

    // Constructor
    public TED(String accountNumber, String agentNumber) {
        this.accountNumber = accountNumber;
        this.agentNumber = agentNumber;
    }
    // Getters and setters
    @Override
    public String getName() {
        return "TED";
    }

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

    // Overridden Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TED ted = (TED) o;
        return Objects.equals(accountNumber, ted.accountNumber) && Objects.equals(agentNumber, ted.agentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, agentNumber);
    }

    @Override
    public String toString() {
        return "TED{" +
                "accountNumber='" + accountNumber + '\'' +
                ", agentNumber='" + agentNumber + '\'' +
                '}';
    }

    // Methods
    public boolean pay(String accountNumber) {
        return this.accountNumber.equals(accountNumber);
    }
}
