package com.lucas.ticketsalesmanager.exception.event;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class EventNotFoundException extends TicketSalesManagerException {
    public EventNotFoundException(String eventId) {
        super("Event not found.", "Event with ID: " + eventId + " not found in the system.");
    }
}

