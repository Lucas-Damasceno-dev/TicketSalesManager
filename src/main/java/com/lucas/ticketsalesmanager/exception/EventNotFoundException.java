package com.lucas.ticketsalesmanager.exception;

public class EventNotFoundException extends TicketSalesManagerException {
    public EventNotFoundException(String eventId) {
        super("Event not found.", "Event with ID: " + eventId + " not found in the system.");
    }
}

