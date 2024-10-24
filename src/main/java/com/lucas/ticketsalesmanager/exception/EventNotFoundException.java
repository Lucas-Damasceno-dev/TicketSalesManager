package com.lucas.ticketsalesmanager.exception;

public class EventNotFoundException extends TicketSalesManagerException {
    public EventNotFoundException(String eventId) {
        super("Evento não encontrado.", "Evento com ID: " + eventId + " não encontrado no sistema.");
    }
}

