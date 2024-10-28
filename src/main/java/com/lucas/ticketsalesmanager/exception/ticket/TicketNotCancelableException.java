package com.lucas.ticketsalesmanager.exception.ticket;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class TicketNotCancelableException extends TicketSalesManagerException {
    public TicketNotCancelableException(String userMessage, String logMessage) {
        super(userMessage, logMessage);
    }
}
