package com.lucas.ticketsalesmanager.exception.purchase;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class PurchaseException extends TicketSalesManagerException {
    public PurchaseException(String userMessage, String logMessage) {
        super(userMessage, logMessage);
    }
}

