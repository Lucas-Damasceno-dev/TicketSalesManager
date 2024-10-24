package com.lucas.ticketsalesmanager.models.paymentMethod;

import java.util.Date;
import java.util.Objects;

public class Card extends Payment {
    // Atributos
    private String numberCard;
    private String cvv;
    private Date expDate;
    private boolean isCreditCard;

    // Construct
    public Card(String numberCard, String cvv, Date expDate, boolean isCreditCard) {
        this.numberCard = numberCard;
        this.cvv = cvv;
        this.expDate = expDate;
        this.isCreditCard = isCreditCard;
    }

    // Getters and setters
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

    // Methods overloadind
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return isCreditCard == card.isCreditCard && Objects.equals(numberCard, card.numberCard) && Objects.equals(cvv, card.cvv) && Objects.equals(expDate, card.expDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberCard, cvv, expDate, isCreditCard);
    }
    @Override
    public String toString() {
        return "Card{" +
                "numberCard='" + numberCard + '\'' +
                ", cvv='" + cvv + '\'' +
                ", expDate=" + expDate +
                ", isCreditCard=" + isCreditCard +
                '}';
    }

    // Methods
    @Override
    public boolean pay(String cvv) {
        if (cvv.equals(this.cvv)) {
            return expDate.after(new Date());
        }
        return false;
    }
}
