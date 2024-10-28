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

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    // Validate the payment details, to be implemented by subclasses
    public abstract boolean validate(String paymentDetails);

    // Execute the payment, to be implemented by subclasses
    public abstract boolean executePayment();

    // Unified payment process that first validates then executes
    public boolean processPayment(String paymentDetails) {
        this.paymentDetails = paymentDetails;
        if (validate(paymentDetails)) {
            return executePayment();
        }
        return false;
    }
}
