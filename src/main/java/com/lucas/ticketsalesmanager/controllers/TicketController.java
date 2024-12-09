/***********************************************************************************************
Author: LUCAS DA CONCEIÇÃO DAMASCENO
Curricular Component: EXA 863 - MI Programming - 2024.2 - TP01
Completed on: 10/24/2024
I declare that this code was prepared by me individually and does not contain any
code snippet from another colleague or another author, such as from books and
handouts, and web pages or electronic documents. Any piece of code
by someone other than mine is highlighted with a citation for the author and source
of the code, and I am aware that these excerpts will not be considered for evaluation purposes
************************************************************************************************/

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Controller responsible for managing ticket operations in the system.
 * This class provides methods to purchase, cancel, reactivate, and list tickets.
 */
public class TicketController {
    private final TicketDAO ticketDAO;

    /**
     * Constructs a TicketController and initializes the TicketDAO.
     */
    public TicketController() {
        this.ticketDAO = new TicketDAO();
    }

    /**
     * Purchases a ticket for a specified event and seat.
     *
     * @param user The user purchasing the ticket.
     * @param event The event for which the ticket is being purchased.
     * @param price The price of the ticket.
     * @param seat The seat associated with the ticket.
     * @param paymentMethod The payment method used for the purchase.
     * @return The purchased ticket.
     * @throws PurchaseException If the ticket purchase fails.
     * @throws MessagingException If there is an issue with sending a confirmation message.
     * @throws TicketAlreadyExistsException If a ticket for the specified seat and event already exists.
     */
    public Ticket purchaseTicket(User user, Event event, float price, String seat, Payment paymentMethod)
            throws PurchaseException, MessagingException, TicketAlreadyExistsException {
        Ticket existingTicket = ticketDAO.findTicketByEventAndSeat(event, seat);
        if (existingTicket != null) {
            throw new TicketAlreadyExistsException("Ticket already exists for the specified seat and event.",
                    "Attempt to purchase an already existing ticket.");
        }

        Ticket ticket = new Ticket(event, price, seat);
        Purchase purchase = new Purchase(user, ticket, paymentMethod);
        if (purchase.processPurchase(user, ticket, paymentMethod)) {
            ticketDAO.addTicket(ticket);
            return ticket;
        }
        throw new PurchaseException("Failed to purchase ticket.", "Ticket purchase process failed.");
    }

    /**
     * Cancels a ticket for a specified user.
     *
     * @param user The user attempting to cancel the ticket.
     * @param ticket The ticket to be canceled.
     * @return True if the cancellation is successful.
     * @throws TicketNotCancelableException If the ticket cannot be canceled.
     * @throws TicketAlreadyCanceledException If the ticket has already been canceled.
     * @throws TicketNotFoundException If the ticket cannot be found.
     */
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

    /**
     * Reactivates a canceled ticket for a specified user.
     *
     * @param user The user attempting to reactivate the ticket.
     * @param ticket The ticket to be reactivated.
     * @return True if the reactivation is successful.
     * @throws TicketNotFoundException If the ticket cannot be found.
     * @throws TicketAlreadyReactivatedException If the ticket is already active.
     * @throws InvalidTicketException If the user is not authorized to reactivate the ticket.
     */
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

    /**
     * Lists all tickets purchased by a specified user.
     *
     * @param user The user whose purchased tickets are to be listed.
     * @return A list of tickets purchased by the user.
     */
    public List<Ticket> listPurchasedTickets(User user) {
        return user.getTickets();
    }

    /**
     * Finds a ticket based on user, event, and seat.
     *
     * @param user The user associated with the ticket.
     * @param event The event associated with the ticket.
     * @param seat The seat associated with the ticket.
     * @return The found ticket.
     * @throws TicketNotFoundException If the ticket cannot be found for the specified user, event, and seat.
     */
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
