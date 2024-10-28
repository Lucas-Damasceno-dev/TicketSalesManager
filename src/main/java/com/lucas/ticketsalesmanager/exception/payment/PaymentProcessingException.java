package com.lucas.ticketsalesmanager.exception.payment;

public class PaymentProcessingException extends PaymentException {
    public PaymentProcessingException() {
        super("Erro ao processar o pagamento. Por favor, tente novamente.",
                "Falha ao processar o pagamento devido a problemas internos.");
    }
}

