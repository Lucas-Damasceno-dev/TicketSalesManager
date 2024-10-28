package com.lucas.ticketsalesmanager.exception.payment;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class PaymentException extends TicketSalesManagerException {
    public PaymentException(String userMessage, String logMessage) {
        super(userMessage, logMessage);
    }
}

