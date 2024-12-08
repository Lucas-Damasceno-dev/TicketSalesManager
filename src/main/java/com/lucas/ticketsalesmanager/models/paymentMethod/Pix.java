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

/**
 * The Pix class represents a Pix payment method, a popular instant payment system in Brazil.
 * It extends the Payment class and provides functionality specific to Pix transactions.
 */
public class Pix extends Payment {
    private String accessKey;

    /**
     * Constructs a new Pix payment method with the specified access key.
     *
     * @param accessKey The access key for the Pix payment.
     */
    public Pix(String accessKey) {
        this.accessKey = accessKey;
    }

    // Getters and Setters
    /**
     * Gets the access key associated with the Pix payment.
     *
     * @return The access key.
     */
    public String getAccessKey() {
        return accessKey;
    }

    /**
     * Sets the access key for the Pix payment.
     *
     * @param accessKey The new access key.
     */
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    // Methods overriding
    /**
     * Validates the payment details for the Pix payment.
     *
     * @param paymentDetails The details to validate, typically the access key.
     * @return {@code true} if the payment details are valid, {@code false} otherwise.
     */
    @Override
    public boolean validate(String paymentDetails) {
        return true;
    }

    /**
     * Executes the Pix payment process.
     *
     * @return {@code true} if the payment was processed successfully, {@code false} otherwise.
     */
    @Override
    public boolean executePayment() {
        System.out.println("Processing Pix payment...");
        return true; // Simulate successful payment processing
    }
}
