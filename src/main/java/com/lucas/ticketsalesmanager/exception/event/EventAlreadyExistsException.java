package com.lucas.ticketsalesmanager.exception.event;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class EventAlreadyExistsException extends TicketSalesManagerException {
    public EventAlreadyExistsException(String eventName) {
        super("Event already exists with the name: " + eventName, "Attempted to add duplicate event: " + eventName);
    }
}
