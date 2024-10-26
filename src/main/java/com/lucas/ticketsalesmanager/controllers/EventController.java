package com.lucas.ticketsalesmanager.controllers;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.exception.EventNotFoundException;
import com.lucas.ticketsalesmanager.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventController {

    private final ArrayList<Event> events;

    public EventController() {
        this.events = new ArrayList<>();
    }

    public Event registerEvent(User user, String name, String description, Date date) throws UserNotFoundException, SecurityException {
        if (user == null) {
            throw new UserNotFoundException("null");
        }
        if (!user.isAdmin()) {
            throw new SecurityException("Only administrators can register events.");
        }
        Event event = new Event(name, description, date);
        events.add(event);
        return event;
    }

    public void addEventSeat(String eventName, String seat) throws EventNotFoundException {
        Event event = getEventByName(eventName);
        event.addSeat(seat);
    }

    public List<Event> listAvailableEvents() {
        List<Event> availableEvents = new ArrayList<>();
        for (Event event : events) {
            if (event.isActive()) {
                availableEvents.add(event);
            }
        }
        return availableEvents;
    }

    public Event getEventByName(String eventName) throws EventNotFoundException {
        for (Event event : events) {
            if (event.getName().equalsIgnoreCase(eventName)) {
                return event;
            }
        }
        throw new EventNotFoundException(eventName);
    }
}
