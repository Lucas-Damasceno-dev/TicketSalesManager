package com.lucas.ticketsalesmanager.persistence;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public void addTicket(Ticket ticket) {
        List<Ticket> tickets = ticketDao.readData();
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
        tickets.add(ticket);
        ticketDao.writeData(tickets);
    }/*
   // Create some events to associate with tickets
    Calendar calendar = Calendar.getInstance();
    calendar.set(2024, Calendar.SEPTEMBER, 10);
    Date date = calendar.getTime();

    Event event1 = new Event("Rock show", "Band XYZ", date);
    Event event2 = new Event("Concert", "Bach", date);

    // Create tome tickets to test
    List<Ticket> tickets = new ArrayList<>();
    tickets.add(new Ticket(event1, 50.0f, "A1"));
    tickets.add(new Ticket(event2, 30.0f, "B2"));

    // Save tickets to file
    ticketDao.writeData(tickets);
    System.out.println("Tickets saved successfully!");

    // Read tickets from the archive
    List<Ticket> loadedTickets = ticketDao.readData();
    if (loadedTickets != null) {
        System.out.println("Uploaded tickets:");
        for (Ticket ticket : loadedTickets) {
            System.out.println(ticket);
        }
    } else {
        System.out.println("No tickets found or error loading.");
    }*/
}
