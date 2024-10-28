package com.lucas.ticketsalesmanager.exception.ticket;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class TicketAlreadyCanceledException extends TicketSalesManagerException {
    public TicketAlreadyCanceledException(String userMessage, String logMessage) {
        super(userMessage, logMessage);
    }
}

