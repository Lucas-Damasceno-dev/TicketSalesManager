package com.lucas.ticketsalesmanager.exception.event;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class EventUpdateException extends TicketSalesManagerException {
    public EventUpdateException(String eventName, String reason) {
        super("Unable to update event " + eventName + ": " + reason, "Failed to update event: " + eventName + ". Reason: " + reason);
    }
}
