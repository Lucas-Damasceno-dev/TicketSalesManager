package com.lucas.ticketsalesmanager.persistence;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Ticket;


public class TicketDAO {
    private static final String FILE_PATH = "tickets.json";
    private static final Type ticketListType = new TypeToken<List<Ticket>>(){}.getType();
    private final DataAccessObject<Ticket> ticketDao;

    public TicketDAO() {
        this.ticketDao = new DataAccessObject<>(FILE_PATH, ticketListType);
    }

    // Add a new ticket
    public boolean addTicket(Ticket ticket) {
        List<Ticket> tickets = ticketDao.readData();
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
        tickets.add(ticket);
        ticketDao.writeData(tickets);
        return true;
    }

    // List all tickets
    public List<Ticket> listTickets() {
        List<Ticket> tickets = ticketDao.readData();
        return tickets != null? tickets : new ArrayList<>();
    }

    // Find ticket by event and seat
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

    // Cancel a ticket
    public boolean cancelTicket(Ticket ticket) {
        List<Ticket> tickets = ticketDao.readData();
        if (tickets!= null && tickets.contains(ticket)) {
            tickets.remove(ticket);
            ticket.cancel();
            ticketDao.writeData(tickets);
            return true;
        }
        return false;
    }

    // Reactivate a canceled ticket
    public boolean reactivateTicket(Ticket ticket) {
        List<Ticket> tickets = ticketDao.readData();
        if (tickets!= null && tickets.contains(ticket)) {
            ticket.reactivate();
            ticketDao.writeData(tickets);
            return true;
        }
        return false;
    }
}
