package com.lucas.ticketsalesmanager.models.paymentMethod;

import com.lucas.ticketsalesmanager.models.Ticket;
import java.util.Objects;

public class PhysicalMoney {
    // Attributes
    private double amount;
    private Ticket ticket;

    // Constructor
    public PhysicalMoney(double amount, Ticket ticket) {
        this.amount = amount;
        this.ticket = ticket;
    }

    // Getters and Setters
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

    // Overridden Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysicalMoney that = (PhysicalMoney) o;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(ticket, that.ticket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, ticket);
    }

    @Override
    public String toString() {
        return "PhysicalMoney{" +
                "amount=" + amount +
                ", ticket=" + ticket +
                '}';
    }

    // Methods
    public boolean pay(double amount, Ticket ticket) {
        return !(amount < ticket.getPrice());
    }
}
