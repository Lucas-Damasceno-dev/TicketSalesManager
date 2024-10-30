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
 * The Payment class is an abstract representation of a payment method.
 * Subclasses must implement the methods for validating and executing payments.
 */
public abstract class Payment {
    // Attributes
    private String name;
    private String paymentDetails;

    // Getters and Setters

    /**
     * Gets the name of the payment method.
     *
     * @return The name of the payment method.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the payment method.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the payment details associated with the payment method.
     *
     * @return The payment details.
     */
    public String getPaymentDetails() {
        return paymentDetails;
    }

    /**
     * Sets the payment details for the payment method.
     *
     * @param paymentDetails The payment details to set.
     */
    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    // Abstract methods for subclasses to implement

    /**
     * Validates the payment details.
     *
     * @param paymentDetails The details to validate.
     * @return {@code true} if the details are valid, {@code false} otherwise.
     */
    public abstract boolean validate(String paymentDetails);

    /**
     * Executes the payment process.
     *
     * @return {@code true} if the payment was successful, {@code false} otherwise.
     */
    public abstract boolean executePayment();

    // Unified payment process that validates and then executes the payment
    /**
     * Processes the payment by first validating the payment details and then executing the payment.
     *
     * @param paymentDetails The details of the payment to process.
     * @return {@code true} if the payment was processed successfully, {@code false} otherwise.
     */
    public boolean processPayment(String paymentDetails) {
        this.paymentDetails = paymentDetails;
        if (validate(paymentDetails)) {
            return executePayment();
        }
        return false; // Return false if validation fails
    }
}
