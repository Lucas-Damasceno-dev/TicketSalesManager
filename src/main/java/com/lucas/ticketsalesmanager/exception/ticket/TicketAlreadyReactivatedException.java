package com.lucas.ticketsalesmanager.exception.ticket;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class TicketAlreadyReactivatedException extends TicketSalesManagerException {
    public TicketAlreadyReactivatedException(String userMessage, String logMessage) {
        super(userMessage, logMessage);
    }
}

