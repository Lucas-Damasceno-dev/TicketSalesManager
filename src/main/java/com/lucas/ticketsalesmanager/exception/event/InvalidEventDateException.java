package com.lucas.ticketsalesmanager.exception.event;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class InvalidEventDateException extends TicketSalesManagerException {
    public InvalidEventDateException(String date) {
        super("Invalid event date: " + date, "Event date provided is invalid or in the past: " + date);
    }
}
