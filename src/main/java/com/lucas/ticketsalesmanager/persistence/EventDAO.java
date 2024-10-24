package com.lucas.ticketsalesmanager.persistence;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lucas.ticketsalesmanager.models.Event;


public class EventDAO {
    public static void main(String[] args) {
        // Define the file path where events will be saved
        String filePath = "events.json";

        // Define the type for the list of events
        Type eventListType = new TypeToken<List<Event>>(){}.getType();

        // Create an instance of DataAccessObject for events
        DataAccessObject<Event> eventDao = new DataAccessObject<>(filePath, eventListType);

        // Create some events for testing
        List<Event> events = new ArrayList<>();
        events.add(new Event("Concert", "A great musical concert", new Date(System.currentTimeMillis() + 86400000))); // Event in 1 day
        events.add(new Event("Theater Play", "An amazing theater play", new Date(System.currentTimeMillis() + 172800000))); // Event in 2 days

        // Save the events to the file
        eventDao.writeData(events);
        System.out.println("Events saved successfully!");

        // Read the events from the file
        List<Event> loadedEvents = eventDao.readData();
        if (loadedEvents != null) {
            System.out.println("Loaded events:");
            for (Event event : loadedEvents) {
                System.out.println(event);
            }
        } else {
            System.out.println("No events found or error loading.");
        }
    }
}
