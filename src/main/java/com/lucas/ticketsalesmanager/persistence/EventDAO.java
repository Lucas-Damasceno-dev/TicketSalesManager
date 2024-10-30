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

package com.lucas.ticketsalesmanager.persistence;

import com.google.gson.reflect.TypeToken;
import com.lucas.ticketsalesmanager.models.Event;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The EventDAO class provides methods for managing event data,
 * allowing for operations such as adding, updating, deleting,
 * and retrieving events stored in a JSON file.
 */
public class EventDAO {
    private static final String FILE_PATH = "events.json";
    private static final Type eventListType = new TypeToken<List<Event>>(){}.getType();
    private final DataAccessObject<Event> eventDao;

    /**
     * Constructs an EventDAO instance and initializes the data access object
     * with the specified file path and type for event lists.
     */
    public EventDAO() {
        this.eventDao = new DataAccessObject<>(FILE_PATH, eventListType);
    }

    /**
     * Adds a new event to the list.
     *
     * @param event The event to be added.
     */
    public void addEvent(Event event) {
        List<Event> events = eventDao.readData();
        if (events == null) {
            events = new ArrayList<>();
        }
        events.add(event);
        eventDao.writeData(events);
    }

    /**
     * Lists all events.
     *
     * @return A list of all events, or an empty list if no events are found.
     */
    public List<Event> listEvents() {
        List<Event> events = eventDao.readData();
        return events != null ? events : new ArrayList<>();
    }

    /**
     * Finds an event by its name.
     *
     * @param name The name of the event to find.
     * @return The Event object if found, or null if not found.
     */
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

    /**
     * Updates the information of an existing event.
     *
     * @param event The event with updated information.
     * @return true if the event was updated successfully, false otherwise.
     */
    public boolean updateEvent(Event event) {
        List<Event> events = eventDao.readData();
        if (events != null) {
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

    /**
     * Deletes an event by its name.
     *
     * @param name The name of the event to be deleted.
     * @return true if the event was deleted successfully, false otherwise.
     */
    public boolean deleteEvent(String name) {
        List<Event> events = eventDao.readData();
        if (events != null) {
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

    /**
     * Finds an event by its date.
     *
     * @param date The date of the event to find.
     * @return The Event object if found, or null if not found.
     */
    public Event findEventByDate(Date date) {
        List<Event> events = eventDao.readData();
        if (events != null) {
            for (Event event : events) {
                if (event.getDate().equals(date)) {
                    return event;
                }
            }
        }
        return null;
    }

    /**
     * Finds an event by its description.
     *
     * @param description The description of the event to find.
     * @return The Event object if found, or null if not found.
     */
    public Event findEventByDescription(String description) {
        List<Event> events = eventDao.readData();
        if (events != null) {
            for (Event event : events) {
                if (event.getDescription().equals(description)) {
                    return event;
                }
            }
        }
        return null;
    }
}
