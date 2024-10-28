package com.lucas.ticketsalesmanager.exception.event;

import com.lucas.ticketsalesmanager.exception.TicketSalesManagerException;

public class SeatUnavailableException extends TicketSalesManagerException {
    public SeatUnavailableException(String eventName, String seat) {
        super("Seat " + seat + " is unavailable for event " + eventName, "Attempted to reserve unavailable seat: " + seat + " for event: " + eventName);
    }
}
