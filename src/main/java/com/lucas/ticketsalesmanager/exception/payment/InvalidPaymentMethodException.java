package com.lucas.ticketsalesmanager.exception.payment;

public class InvalidPaymentMethodException extends PaymentException {
    public InvalidPaymentMethodException(String paymentMethod) {
        super("Método de pagamento inválido.", "Método de pagamento " + paymentMethod + " é inválido.");
    }
}

