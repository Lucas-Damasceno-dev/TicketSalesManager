package com.lucas.ticketsalesmanager.models.paymentMethod;

import java.util.Date;

public class Card extends Payment {
    private String numberCard;
    private String cvv;
    private Date expDate;
    private boolean isCreditCard;

    public Card(String numberCard, String cvv, Date expDate, boolean isCreditCard) {
        this.numberCard = numberCard;
        this.cvv = cvv;
        this.expDate = expDate;
        this.isCreditCard = isCreditCard;
    }

    // Getters e Setters
    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public boolean isCreditCard() {
        return isCreditCard;
    }

    public void setCreditCard(boolean creditCard) {
        isCreditCard = creditCard;
    }

    // Methods overriding
    @Override
    public boolean validate(String paymentDetails) {
        return paymentDetails.equals(cvv) && expDate.after(new Date());
    }

    @Override
    public boolean executePayment() {
        System.out.println("Processing card payment...");
        return true;
    }
}
