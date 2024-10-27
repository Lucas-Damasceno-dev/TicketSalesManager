package com.lucas.ticketsalesmanager.persistence;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lucas.ticketsalesmanager.models.Event;


public class EventDAO {
    private static final String FILE_PATH = "events.json";
    private static final Type eventListType = new TypeToken<List<Event>>(){}.getType();
    private final DataAccessObject<Event> eventDao;

    public EventDAO() {
        this.eventDao = new DataAccessObject<>(FILE_PATH, eventListType);
    }

    // Add a new event
    public boolean addEvent(Event event) {
        List<Event> events = eventDao.readData();
        if (events == null) {
            events = new ArrayList<>();
        }
        events.add(event);
        eventDao.writeData(events);
        return true;
    }

    // List all events
    public List<Event> listEvents() {
        List<Event> events = eventDao.readData();
        return events!= null? events : new ArrayList<>();
    }

    // Find event by name
    public Event findEventByName(String name) {
        List<Event> events = eventDao.readData();
        if (events != null) {
            for (Event event : events) {
                if (event.getName().equals(name)) {
                    return event;
                }
            }
        }
        return null;
    }

    // Update event information
    public boolean updateEvent(Event event) {
        List<Event> events = eventDao.readData();
        if (events!= null) {
            for (int i = 0; i < events.size(); i++) {
                if (events.get(i).getName().equals(event.getName())) {
                    events.set(i, event);
                    eventDao.writeData(events);
                    return true;
                }
            }
        }
        return false;
    }

    // Delete event by name
    public boolean deleteEvent(String name) {
        List<Event> events = eventDao.readData();
        if (events!= null) {
            for (int i = 0; i < events.size(); i++) {
                if (events.get(i).getName().equals(name)) {
                    events.remove(i);
                    eventDao.writeData(events);
                    return true;
                }
            }
        }
        return false;
    }

    // Find event by date
    public Event findEventByDate(Date date) {
        List<Event> events = eventDao.readData();
        if (events!= null) {
            for (Event event : events) {
                if (event.getDate().equals(date)) {
                    return event;
                }
            }
        }
        return null;
    }

    // Find event by description
    public Event findEventByDescription(String description) {
        List<Event> events = eventDao.readData();
        if (events!= null) {
            for (Event event : events) {
                if (event.getDescription().equals(description)) {
                    return event;
                }
            }
        }
        return null;
    }
}
