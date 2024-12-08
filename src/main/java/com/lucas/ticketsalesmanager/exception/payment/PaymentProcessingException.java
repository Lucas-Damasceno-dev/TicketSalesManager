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

/**
 * Exception thrown when an error occurs while processing a payment.
 *
 * This exception extends the {@link PaymentException} class.
 * The error message to the user is "Error processing payment.
 * Please try again." and the message to the log is "Failed to
 * process payment due to internal issues."
 */
package com.lucas.ticketsalesmanager.exception.payment;

public class PaymentProcessingException extends PaymentException {
    public PaymentProcessingException() {
        super("Erro ao processar o pagamento. Por favor, tente novamente.",
                "Falha ao processar o pagamento devido a problemas internos.");
    }
}
