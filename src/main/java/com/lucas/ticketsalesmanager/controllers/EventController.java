package com.lucas.ticketsalesmanager.controllers;

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

public class EventController {

    private final EventDAO eventDAO;

    public EventController() {
        this.eventDAO = new EventDAO();
    }

    // Register event, throwing exception if event already exists or has invalid data
    public Event registerEvent(User user, Event event) throws EventAlreadyExistsException, InvalidEventDateException {
        if (!user.isAdmin()) {
            throw new IllegalStateException();
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

    public String addEventSeat(String eventName, String seat) throws EventNotFoundException, SeatUnavailableException, EventUpdateException {
        Event event = getEventByName(eventName);
        if (!event.getAvailableSeats().contains(seat)) {
            throw new SeatUnavailableException(eventName, "Seat " + seat + " is unavailable.");
        }
        event.addSeat(seat);
        boolean updated = eventDAO.updateEvent(event);
        if (!updated) {
            throw new EventUpdateException(eventName, "Failed to update event with new seat.");
        }
        return seat;
    }

    public String removeEventSeat(String name, String a1) throws EventUpdateException, EventNotFoundException {
        Event event = getEventByName(name);
        event.removeSeat(a1);
        boolean updated = eventDAO.updateEvent(event);
        if (!updated) {
            throw new EventUpdateException(name, "Failed to update event with removed seat.");
        }
        return a1;
    }

    // List all events
    public List<Event> listEvents() {
        return eventDAO.listEvents();
    }

    // Find an event by name, throwing exception if not found
    public Event getEventByName(String eventName) throws EventNotFoundException {
        Event event = eventDAO.findEventByName(eventName);
        if (event == null) {
            throw new EventNotFoundException(eventName);
        }
        return event;
    }

    // List only available events
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
