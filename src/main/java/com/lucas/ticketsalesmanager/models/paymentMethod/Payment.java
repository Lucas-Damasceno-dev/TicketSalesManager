package com.lucas.ticketsalesmanager.models.paymentMethod;

public abstract class Payment {
    // Attributes
    private String name;
    private String paymentDetails;

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Abstract methods
    public abstract boolean pay(String paymentDetails);
}
