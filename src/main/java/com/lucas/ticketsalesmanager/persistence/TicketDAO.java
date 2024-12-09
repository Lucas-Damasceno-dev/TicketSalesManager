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

package com.lucas.ticketsalesmanager.persistence;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Ticket;

/**
 * The TicketDAO class provides data access methods for managing ticket data.
 * It handles operations such as adding, listing, finding, canceling, and
 * reactivating tickets stored in a JSON file.
 */
public class TicketDAO {
    private static final String FILE_PATH = "tickets.json";
    private static final Type ticketListType = new TypeToken<List<Ticket>>(){}.getType();
    private final DataAccessObject<Ticket> ticketDao;

    /**
     * Constructs a TicketDAO instance, initializing the data access object
     * with the specified file path and type for ticket lists.
     */
    public TicketDAO() {
        this.ticketDao = new DataAccessObject<>(FILE_PATH, ticketListType);
    }

    /**
     * Adds a new ticket to the ticket list.
     *
     * @param ticket The ticket to be added.
     * @return true if the ticket was added successfully.
     */
    public boolean addTicket(Ticket ticket) {
        List<Ticket> tickets = ticketDao.readData();
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
        tickets.add(ticket);
        ticketDao.writeData(tickets);
        return true;
    }

    /**
     * Retrieves all tickets from the ticket list.
     *
     * @return A list of all tickets, or an empty list if no tickets are found.
     */
    public List<Ticket> listTickets() {
        List<Ticket> tickets = ticketDao.readData();
        return tickets != null ? tickets : new ArrayList<>();
    }

    /**
     * Finds a ticket by its associated event and seat number.
     *
     * @param event The event associated with the ticket.
     * @param seat The seat number of the ticket.
     * @return The Ticket object if found, or null if not found.
     */
    public Ticket findTicketByEventAndSeat(Event event, String seat) {
        List<Ticket> tickets = ticketDao.readData();
        if (tickets != null) {
            for (Ticket ticket : tickets) {
                if (ticket.getEvent().equals(event) && ticket.getSeat().equals(seat)) {
                    return ticket;
                }
            }
        }
        return null;
    }

    /**
     * Cancels a specified ticket.
     *
     * @param ticket The ticket to be canceled.
     * @return true if the ticket was canceled successfully, false otherwise.
     */
    public boolean cancelTicket(Ticket ticket) {
        List<Ticket> tickets = ticketDao.readData();
        if (tickets != null && tickets.contains(ticket)) {
            tickets.remove(ticket);
            ticket.cancel();
            ticketDao.writeData(tickets);
            return true;
        }
        return false;
    }

    /**
     * Reactivates a previously canceled ticket.
     *
     * @param ticket The ticket to be reactivated.
     * @return true if the ticket was reactivated successfully, false otherwise.
     */
    public boolean reactivateTicket(Ticket ticket) {
        List<Ticket> tickets = ticketDao.readData();
        if (tickets != null && tickets.contains(ticket)) {
            ticket.reactivate();
            ticketDao.writeData(tickets);
            return true;
        }
        return false;
    }
}
