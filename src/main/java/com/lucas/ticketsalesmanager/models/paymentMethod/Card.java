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

import java.util.Date;

/**
 * The Card class represents a payment method using a credit or debit card.
 * It contains information such as card number, CVV, expiration date, and card type.
 */
public class Card extends Payment {
    private String numberCard;
    private String cvv;
    private Date expDate;
    private boolean isCreditCard;

    /**
     * Constructs a new Card object with the specified details.
     *
     * @param numberCard The card number.
     * @param cvv The card's CVV.
     * @param expDate The expiration date of the card.
     * @param isCreditCard Indicates whether the card is a credit card.
     */
    public Card(String numberCard, String cvv, Date expDate, boolean isCreditCard) {
        this.numberCard = numberCard;
        this.cvv = cvv;
        this.expDate = expDate;
        this.isCreditCard = isCreditCard;
    }

    // Getters and Setters

    /**
     * Gets the card number.
     *
     * @return The card number.
     */
    public String getNumberCard() {
        return numberCard;
    }

    /**
     * Sets the card number.
     *
     * @param numberCard The card number to set.
     */
    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    /**
     * Gets the card's CVV.
     *
     * @return The card's CVV.
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Sets the card's CVV.
     *
     * @param cvv The CVV to set.
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    /**
     * Gets the expiration date of the card.
     *
     * @return The expiration date.
     */
    public Date getExpDate() {
        return expDate;
    }

    /**
     * Sets the expiration date of the card.
     *
     * @param expDate The expiration date to set.
     */
    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    /**
     * Checks if the card is a credit card.
     *
     * @return {@code true} if the card is a credit card, {@code false} otherwise.
     */
    public boolean isCreditCard() {
        return isCreditCard;
    }

    /**
     * Sets whether the card is a credit card.
     *
     * @param creditCard Indicates if the card is a credit card.
     */
    public void setCreditCard(boolean creditCard) {
        isCreditCard = creditCard;
    }

    // Methods overriding

    /**
     * Validates the payment details for the card.
     *
     * @param paymentDetails The details to validate (should be the CVV).
     * @return {@code true} if the CVV is correct and the card is not expired, {@code false} otherwise.
     */
    @Override
    public boolean validate(String paymentDetails) {
        return true;
    }

    /**
     * Executes the payment process for the card.
     *
     * @return {@code true} if the payment was successful, {@code false} otherwise.
     */
    @Override
    public boolean executePayment() {
        System.out.println("Processing card payment...");
        return true;
    }
}
