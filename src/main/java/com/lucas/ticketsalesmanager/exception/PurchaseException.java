package com.lucas.ticketsalesmanager.exception;

public class PurchaseException extends TicketSalesManagerException {
    public PurchaseException(String errorMessage) {
        super("Erro ao processar a compra.", errorMessage);
    }
}

