package com.lucas.ticketsalesmanager.controllers;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.exception.PurchaseException;

public class TicketController {
    public Ticket purchaseTicket(User user, Event event, String seat) throws PurchaseException {
        if (event == null || !event.getAvailableSeats().contains(seat)) {
            throw new PurchaseException("Seat " + seat + " is unavailable.");
        }
        Ticket ticket = new Ticket(event, 100.0F, seat);
        event.removeSeat(seat);
        user.getTickets().add(ticket);
        return ticket;
    }

    public boolean cancelPurchase(User user, Ticket ticket) {
        if (user.getTickets().contains(ticket)) {
            user.getTickets().remove(ticket);
            ticket.cancel();
            ticket.getEvent().addSeat(ticket.getSeat());
            return true;
        }
        return false;
    }
}
