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

import com.lucas.ticketsalesmanager.exception.user.UserNotAuthorizedException;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.exception.event.EventNotFoundException;
import com.lucas.ticketsalesmanager.exception.event.SeatUnavailableException;
import com.lucas.ticketsalesmanager.exception.event.InvalidEventDateException;
import com.lucas.ticketsalesmanager.exception.event.EventUpdateException;
import com.lucas.ticketsalesmanager.exception.event.EventAlreadyExistsException;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.persistence.EventDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing events, including registration, removal, and seat management.
 */
public class EventController {

    private final EventDAO eventDAO;

    /**
     * Constructs an EventController and initializes the EventDAO.
     */
    public EventController() {
        this.eventDAO = new EventDAO();
    }

    /**
     * Registers a new event, throwing exceptions if the event already exists or has invalid data.
     *
     * @param user The user attempting to register the event.
     * @param event The event to be registered.
     * @return The registered event.
     * @throws EventAlreadyExistsException If an event with the same name already exists.
     * @throws InvalidEventDateException If the event date is invalid.
     * @throws UserNotAuthorizedException If the user is not an admin.
     */
    public Event registerEvent(User user, Event event)
            throws EventAlreadyExistsException, InvalidEventDateException, UserNotAuthorizedException {
        if (!user.isAdmin()) {
            throw new UserNotAuthorizedException(user.getName());
        }
        if (eventDAO.findEventByName(event.getName()) != null) {
            throw new EventAlreadyExistsException(event.getName());
        }
        if (!event.isActive()) {
            String date = String.valueOf(event.getDate());
            throw new InvalidEventDateException(date);
        }
        eventDAO.addEvent(event);
        return event;
    }

    /**
     * Removes an event by its name.
     *
     * @param eventName The name of the event to remove.
     * @throws EventNotFoundException If the event cannot be found.
     */
    public void removeEvent(String eventName) throws EventNotFoundException {
        Event event = getEventByName(eventName);
        if (event.getName().equals(eventName)) {
            eventDAO.deleteEvent(eventName);
            return;
        }
        throw new EventNotFoundException(eventName);
    }

    /**
     * Adds a seat to an existing event.
     *
     * @param eventName The name of the event.
     * @param seat The seat to add.
     * @return The updated event.
     * @throws EventNotFoundException If the event cannot be found.
     * @throws EventUpdateException If the event cannot be updated.
     * @throws SeatUnavailableException If the seat is already reserved.
     */
    public Event addEventSeat(String eventName, String seat)
            throws EventNotFoundException, EventUpdateException, SeatUnavailableException {
        Event event = getEventByName(eventName);
        if (event == null) {
            throw new EventNotFoundException(eventName);
        }
        if (event.getAvailableSeats().contains(seat)) {
            throw new SeatUnavailableException(eventName, "Seat " + seat + " is already reserved.");
        }
        event.addSeat(seat);
        boolean updated = eventDAO.updateEvent(event);
        if (!updated) {
            throw new EventUpdateException(eventName, "Failed to update event with new seat.");
        }
        return event;
    }

    /**
     * Removes a seat from an existing event.
     *
     * @param eventName The name of the event.
     * @param seat The seat to remove.
     * @return The updated event.
     * @throws EventNotFoundException If the event cannot be found.
     * @throws SeatUnavailableException If the seat is not reserved.
     * @throws EventUpdateException If the event cannot be updated.
     */
    public Event removeEventSeat(String eventName, String seat)
            throws EventNotFoundException, SeatUnavailableException, EventUpdateException {
        Event event = getEventByName(eventName);
        if (event == null) {
            throw new EventNotFoundException(eventName);
        }
        if (!event.getAvailableSeats().contains(seat)) {
            throw new SeatUnavailableException(eventName, "Seat " + seat + " is not reserved.");
        }
        event.removeSeat(seat);
        boolean updated = eventDAO.updateEvent(event);
        if (!updated) {
            throw new EventUpdateException(eventName, "Failed to update event by removing seat.");
        }
        return event;
    }

    /**
     * Lists all events in the system.
     *
     * @return A list of all events.
     */
    public List<Event> listEvents() {
        return eventDAO.listEvents();
    }

    /**
     * Finds an event by its name.
     *
     * @param eventName The name of the event.
     * @return The found event.
     * @throws EventNotFoundException If the event cannot be found.
     */
    public Event getEventByName(String eventName) throws EventNotFoundException {
        Event event = eventDAO.findEventByName(eventName);
        if (event == null) {
            throw new EventNotFoundException(eventName);
        }
        return event;
    }

    /**
     * Lists only available events that are currently active.
     *
     * @return A list of available events.
     */
    public List<Event> listAvailableEvents() {
        List<Event> events = eventDAO.listEvents();
        List<Event> availableEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.isActive()) {
                availableEvents.add(event);
            }
        }
        return availableEvents;
    }
}
