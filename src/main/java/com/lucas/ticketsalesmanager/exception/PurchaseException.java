package com.lucas.ticketsalesmanager.exception;

public class PurchaseException extends TicketSalesManagerException {
    public PurchaseException(String errorMessage) {
        super("Error processing purchase.", errorMessage);
    }
}

