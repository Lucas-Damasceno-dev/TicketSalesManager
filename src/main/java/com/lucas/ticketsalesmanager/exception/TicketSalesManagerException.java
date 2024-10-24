package com.lucas.ticketsalesmanager.exception;

public class TicketSalesManagerException extends Exception {
    private final String userMessage;

    public TicketSalesManagerException(String userMessage, String logMessage) {
        super(logMessage); // The log message will be passed to Exception
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
