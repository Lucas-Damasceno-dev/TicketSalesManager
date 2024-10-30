package com.lucas.ticketsalesmanager.controllers;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Purchase;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.exception.purchase.PurchaseException;
import com.lucas.ticketsalesmanager.exception.ticket.*;
import com.lucas.ticketsalesmanager.models.paymentMethod.Payment;
import com.lucas.ticketsalesmanager.persistence.TicketDAO;
import jakarta.mail.MessagingException;

import java.util.List;

public class TicketController {
    private final TicketDAO ticketDAO;

    public TicketController() {
        this.ticketDAO = new TicketDAO();
    }

    // Purchase a ticket
    public Ticket purchaseTicket(User user, Event event, float price, String seat, Payment paymentMethod)
            throws PurchaseException, MessagingException, TicketAlreadyExistsException {
        Ticket existingTicket = ticketDAO.findTicketByEventAndSeat(event, seat);
        if (existingTicket != null) {
            throw new TicketAlreadyExistsException("Ticket already exists for the specified seat and event.",
                    "Attempt to purchase an already existing ticket.");
        }

        Ticket ticket = new Ticket(event, price, seat);
        Purchase purchase = new Purchase(user, ticket, paymentMethod);
        if (ticketDAO.addTicket(ticket) && purchase.processPurchase(user, ticket, paymentMethod)) {
            return ticket;
        }
        throw new PurchaseException("Failed to purchase ticket.", "Ticket purchase process failed.");
    }

    // Cancel a ticket
    public boolean cancelTicket(User user, Ticket ticket)
            throws TicketNotCancelableException, TicketAlreadyCanceledException, TicketNotFoundException {

        if (!ticket.isActive()) {
            throw new TicketAlreadyCanceledException("Ticket is already canceled.",
                    "Attempt to cancel an already canceled ticket.");
        }

        if (!ticketDAO.cancelTicket(ticket)) {
            throw new TicketNotFoundException("Ticket not found.", "Ticket could not be found for cancellation.");
        }

        return true;
    }

    // Reactivate a canceled ticket
    public boolean reactivateTicket(User user, Ticket ticket)
            throws TicketNotFoundException, TicketAlreadyReactivatedException, InvalidTicketException {
        if (!user.isAdmin() || !user.getTickets().contains(ticket)) {
            throw new InvalidTicketException("User is not authorized to reactivate this ticket.",
                    "Non-admin or ticket does not belong to the user.");
        }

        if (ticket.isActive()) {
            throw new TicketAlreadyReactivatedException("Ticket is already active.",
                    "Attempt to reactivate an already active ticket.");
        }

        if (!ticketDAO.reactivateTicket(ticket)) {
            throw new TicketNotFoundException("Ticket not found.", "Ticket could not be found for reactivation.");
        }

        return true;
    }

    // List all purchased tickets by a user
    public List<Ticket> listPurchasedTickets(User user) {
        return user.getTickets();
    }

    // Find a ticket by user, event, and seat
    public Ticket findTicketsByUserAndEventAndSeat(User user, Event event, String seat)
            throws TicketNotFoundException {
        Ticket ticket = ticketDAO.findTicketByEventAndSeat(event, seat);
        if (ticket == null || !user.getTickets().contains(ticket)) {
            throw new TicketNotFoundException("Ticket not found for the specified seat and event.",
                    "Ticket search failed for the provided user, event, and seat.");
        }
        return ticket;
    }
}
