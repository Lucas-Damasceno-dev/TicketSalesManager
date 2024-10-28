package com.lucas.ticketsalesmanager.exception.ticket;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class TicketAlreadyExistsException extends TicketSalesManagerException {
    public TicketAlreadyExistsException(String userMessage, String logMessage) {
        super(userMessage, logMessage);
    }
}
