package com.lucas.ticketsalesmanager.controllers;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Purchase;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.exception.PurchaseException;
import com.lucas.ticketsalesmanager.models.paymentMethod.Payment;
import com.lucas.ticketsalesmanager.persistence.TicketDAO;
import jakarta.mail.MessagingException;

public class TicketController {
    private final TicketDAO ticketDAO;

    public TicketController() {
        this.ticketDAO = new TicketDAO();
    }

    // Purchase a ticket using (TicketDAO and Purchase) class
    public Ticket purchaseTicket(User user, Event event, float price, String seat, Payment paymentMethod) throws PurchaseException, MessagingException {
        Ticket ticket = new Ticket(event, price, seat);
        Purchase purchase = new Purchase(user, ticket, paymentMethod);
        if (ticketDAO.addTicket(ticket) && purchase.processPurchase(user, ticket, paymentMethod)) {
            return ticket;
        }
        throw new PurchaseException("Failed to purchase ticket.");
    }


   // Cancel a ticket using (TicketDAO and Purchase) class
   public boolean cancelTicket(User user, Ticket ticket) {
       if (!user.isAdmin() || !user.getTickets().contains(ticket)) {
           return false;
       }
       return ticketDAO.cancelTicket(ticket);
   }
}
