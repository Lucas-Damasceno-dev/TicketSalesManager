package com.lucas.ticketsalesmanager.controllers;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.exception.*;

import java.util.Date;
import java.util.List;

public class Controller {

    private final UserController userController;
    private final EventController eventController;
    private final TicketController ticketController;

    public Controller() {
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

    public Event registerEvent(User user, String name, String description, Date date) throws UserNotFoundException, SecurityException {
        return eventController.registerEvent(user, name, description, date);
    }

    public void addEventSeat(String eventName, String seat) throws EventNotFoundException {
        eventController.addEventSeat(eventName, seat);
    }

    public Ticket purchaseTicket(User user, String eventName, String seat) throws PurchaseException, EventNotFoundException {
        Event event = eventController.getEventByName(eventName);
        return ticketController.purchaseTicket(user, event, seat);
    }

    public boolean cancelPurchase(User user, Ticket ticket) {
        return ticketController.cancelPurchase(user, ticket);
    }

    public List<Event> listAvailableEvents() {
        return eventController.listAvailableEvents();
    }

    public List<Ticket> listPurchasedTickets(User user) {
        return user.getTickets();
    }
}
