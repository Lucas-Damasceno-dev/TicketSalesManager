package com.lucas.ticketsalesmanager.exception.ticket;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class InvalidTicketException extends TicketSalesManagerException {
    public InvalidTicketException(String userMessage, String logMessage) {
        super(userMessage, logMessage);
    }
}