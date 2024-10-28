package com.lucas.ticketsalesmanager.exception.payment;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class PaymentFailedException extends TicketSalesManagerException {
    public PaymentFailedException(String userMessage, String logMessage) {
        super(userMessage, logMessage);
    }
}