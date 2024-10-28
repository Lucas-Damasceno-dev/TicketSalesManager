package com.lucas.ticketsalesmanager.exception.ticket;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class TicketNotFoundException extends TicketSalesManagerException {
    public TicketNotFoundException(String userMessage, String logMessage) {
        super(userMessage, logMessage);
    }
}

