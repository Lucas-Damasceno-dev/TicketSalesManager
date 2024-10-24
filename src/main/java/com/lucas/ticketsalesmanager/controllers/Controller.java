package com.lucas.ticketsalesmanager.controllers;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.exception.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The Controller class manages events, users, and their interactions, such as event registration, ticket purchase, and cancellation.
 */
public class Controller {

    // Attributes
    /**
     * List of events managed by the controller.
     */
    private final ArrayList<Event> events;

    // Constructor
    /**
     * Constructs a new Controller by initializing the list of events.
     */
    public Controller() {
        this.events = new ArrayList<>();
    }

    // Overridden Methods
    /**
     * Compares this Controller object with another object to check if they are equal, based on the event list.
     *
     * @param o The object to be compared.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Controller that = (Controller) o;
        return Objects.equals(events, that.events);
    }

    /**
     * Returns the hash code for the Controller object, based on the event list.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(events);
    }

    // Class Methods
    /**
     * Registers a new user in the system.
     *
     * @param login The user's login.
     * @param password The user's password.
     * @param name The user's name.
     * @param cpf The user's CPF.
     * @param email The user's email.
     * @param isAdmin Indicates whether the user is an administrator.
     * @return The newly registered {@link User} object.
     */
    public User registerUser(String login, String password, String name, String cpf, String email, boolean isAdmin) {
        return new User(login, password, name, cpf, email, isAdmin);
    }

    /**
     * Updates the user's data if the user's login and password match the user's current credentials.
     *
     * @param user The user trying to update the data.
     */
    public User updateUser(User user, String infoToUpdate, String newInfo) throws UserNotFoundException {
        if (user == null) {
            throw new UserNotFoundException(user.getLogin());
        }

        if (user.login(user.getLogin(), user.getPassword())) {
            switch (infoToUpdate.toLowerCase()) {
                case "login":
                    user.setLogin(newInfo);
                    break;
                case "password":
                    user.setPassword(newInfo);
                    break;
                case "name":
                    user.setName(newInfo);
                    break;
                case "cpf":
                    user.setCpf(newInfo);
                    break;
                case "email":
                    user.setEmail(newInfo);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid information type.");
            }
            return user;
        } else {
            throw new SecurityException("Invalid login or password.");
        }
    }

    /**
     * Registers a new event in the system, if the user is an administrator.
     *
     * @param user The user attempting to register the event.
     * @param name The name of the event.
     * @param description The description of the event.
     * @param date The date of the event.
     * @return The newly registered {@link Event} object.
     * @throws SecurityException If the user is not an administrator.
     */
    public Event registerEvent(User user, String name, String description, Date date) throws SecurityException, UserNotFoundException {
        if (user == null) {
            throw new UserNotFoundException("null");
        }

        if (user.isAdmin()) {
            Event event = new Event(name, description, date);
            events.add(event);
            return event;
        } else {
            throw new SecurityException("Only administrators can register events.");
        }
    }

    /**
     * Adds an available seat to a specific event.
     *
     * @param name The name of the event.
     * @param seat The seat to be added.
     * @throws EventNotFoundException If the event is not found.
     */
    public void addEventSeat(String name, String seat) throws EventNotFoundException {
        for (Event event : events) {
            if (event.getName().equals(name)) {
                event.addSeat(seat);
                return;
            }
        }
        throw new EventNotFoundException(name);
    }

    /**
     * Processes a ticket purchase for a user, removing the seat from the event and adding the ticket to the user's list.
     *
     * @param user The user buying the ticket.
     * @param name The name of the event.
     * @param seat The chosen seat for the ticket.
     * @return The newly purchased {@link Ticket} object.
     * @throws PurchaseException If the seat is unavailable or the event is not found.
     */
    public Ticket purchaseTicket(User user, String name, String seat) throws PurchaseException {
        for (Event event : events) {
            if (event.getName().equals(name)) {
                if (!event.getAvailableSeats().contains(seat)) {
                    throw new PurchaseException("Seat " + seat + " is unavailable.");
                } else {
                    Ticket ticket = new Ticket(event, 100.0F, seat);
                    event.removeSeat(seat);
                    user.getTickets().add(ticket);
                    return ticket;
                }
            }
        }
        throw new PurchaseException("Event " + name + " not found.");
    }

    /**
     * Cancels a user's ticket purchase, reactivating the seat in the corresponding event.
     *
     * @param user The user canceling the purchase.
     * @param ticket The ticket to be canceled.
     * @return {@code true} if the cancellation was successful, {@code false} otherwise.
     */
    public boolean cancelPurchase(User user, Ticket ticket) {
        if (user.getTickets().contains(ticket)) {
            user.getTickets().remove(ticket);
            ticket.cancel();
            ticket.getEvent().addSeat(ticket.getSeat());
            return true;
        }
        return false;
    }

    /**
     * Lists all available events, meaning events that have not yet occurred.
     *
     * @return A list of available events.
     */
    public List<Event> listAvailableEvents() {
        List<Event> availableEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.isActive()) {
                availableEvents.add(event);
            }
        }
        return availableEvents;
    }

    /**
     * Lists all tickets purchased by a user.
     *
     * @param user The user whose tickets will be listed.
     * @return A list of tickets purchased by the user.
     */
    public List<Ticket> listPurchasedTickets(User user) {
        return user.getTickets();
    }
}
