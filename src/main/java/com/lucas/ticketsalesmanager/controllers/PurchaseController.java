package com.lucas.ticketsalesmanager.controllers;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.exception.*;
import com.lucas.ticketsalesmanager.models.paymentMethod.Payment;
import jakarta.mail.MessagingException;

import java.util.Date;
import java.util.List;

public class PurchaseController {
    private final UserController userController;
    private final EventController eventController;
    private final TicketController ticketController;

    public PurchaseController() {
        this.userController = new UserController();
        this.eventController = new EventController();
        this.ticketController = new TicketController();
    }

    public User registerUser(String login, String password, String name, String cpf, String email, boolean isAdmin) {
        return userController.registerUser(login, password, name, cpf, email, isAdmin);
    }

    public User updateUser(User user, String infoToUpdate, String newInfo) throws UserNotFoundException {
        return userController.updateUser(user, infoToUpdate, newInfo);
    }

    public boolean registerEvent(User user, String name, String description, Date date) throws UserNotFoundException, SecurityException {
        if (user.isAdmin()) {
            Event event = new Event(name, description, date);
            return eventController.registerEvent(event);
        }
        return false;
    }

    public void addEventSeat(String eventName, String seat) throws EventNotFoundException {
        eventController.addEventSeat(eventName, seat);
    }

    public Ticket purchaseTicket(User user, String eventName, String seat, float price, Payment paymentMethod) throws PurchaseException, EventNotFoundException, MessagingException {
        Event event = eventController.getEventByName(eventName);
        return ticketController.purchaseTicket(user, event, price, seat, paymentMethod);
    }

    public boolean cancelPurchase(User user, Ticket ticket) {
        return ticketController.cancelTicket(user, ticket);
    }

    public List<Event> listAvailableEvents() {
        return eventController.listAvailableEvents();
    }

    public List<Ticket> listPurchasedTickets(User user) {
        return user.getTickets();
    }
}
