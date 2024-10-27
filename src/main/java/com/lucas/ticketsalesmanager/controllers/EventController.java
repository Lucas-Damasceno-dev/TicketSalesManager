package com.lucas.ticketsalesmanager.controllers;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.exception.EventNotFoundException;
import com.lucas.ticketsalesmanager.exception.UserNotFoundException;
import com.lucas.ticketsalesmanager.persistence.EventDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventController {

    private final EventDAO eventDAO;

    public EventController() {
        this.eventDAO = new EventDAO();
    }

    // Register event using eventDAO
    public boolean registerEvent(Event event) {
        return eventDAO.addEvent(event);
    }

    // make a methods for seat in event using eventDAO
    public boolean addEventSeat(String eventName, String seat) throws EventNotFoundException {
        Event event = getEventByName(eventName);
        event.addSeat(seat);
        return eventDAO.updateEvent(event);
    }

    // make a method for list all events available using eventDAO.listEvents
    public List<Event> listEvents() {
        return eventDAO.listEvents();
    }

    // make a method for find a event available using eventDAO.findEventByName
    public Event getEventByName(String eventName) throws EventNotFoundException {
        Event event = eventDAO.findEventByName(eventName);
        if (event == null) {
            throw new EventNotFoundException(eventName);
        }
        return event;
    }

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
