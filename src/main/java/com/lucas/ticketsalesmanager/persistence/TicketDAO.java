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
    public static void main(String[] args) {
        // Defines the file path where tickets will be saved
        String filePath = "tickets.json";

        // Defines the type of ticket list
        Type ticketListType = new TypeToken<List<Ticket>>(){}.getType();

        // Creates an instance of the DataAccessObject for tickets
        DataAccessObject<Ticket> ticketDao = new DataAccessObject<>(filePath, ticketListType);

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
        }
    }
}
