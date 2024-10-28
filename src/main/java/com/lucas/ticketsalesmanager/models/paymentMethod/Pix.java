package com.lucas.ticketsalesmanager.models.paymentMethod;

public class Pix extends Payment {
    private String accesskey;

    public Pix(String accesskey) {
        this.accesskey = accesskey;
    }

    // Getters e Setters
    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    // Methods overriding
    @Override
    public boolean validate(String paymentDetails) {
        return this.accesskey.equals(paymentDetails);
    }

    @Override
    public boolean executePayment() {
        System.out.println("Processing Pix payment...");
        return true;
    }
}
