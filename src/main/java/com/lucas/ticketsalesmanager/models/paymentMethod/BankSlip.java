package com.lucas.ticketsalesmanager.models.paymentMethod;

import java.util.Objects;

public class BankSlip extends Payment{
    // Atributes
    private String accountNumber;
    private String agentNumber;

    // Constructor
    public BankSlip(String accountNumber, String agentNumber) {
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

    // Methods overloading
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankSlip bankSlip = (BankSlip) o;
        return Objects.equals(accountNumber, bankSlip.accountNumber) && Objects.equals(agentNumber, bankSlip.agentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, agentNumber);
    }

    @Override
    public String toString() {
        return "BankSlip{" +
                "accountNumber='" + accountNumber + '\'' +
                ", agentNumber='" + agentNumber + '\'' +
                '}';
    }

    // Methods
    @Override
    public boolean pay(String number) {
        return true;
    }
}
