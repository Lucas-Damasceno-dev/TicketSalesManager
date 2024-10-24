package com.lucas.ticketsalesmanager.models;

import com.lucas.ticketsalesmanager.service.communication.EventFeedback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * The Event class represents an event that has a name, description, date, and a list of available seats.
 * It also contains the active state of the event, based on the current date.
 */
public class Event {

    // Attributes
    /**
     * The name of the event.
     */
    private final String name;

    /**
     * The description of the event.
     */
    private final String description;

    /**
     * The date on which the event will take place.
     */
    private final Date date;

    /**
     * The list of available seats for the event.
     */
    private final ArrayList<String> availableSeats;

    private final ArrayList<EventFeedback> eventFeedbacks;

    /**
     * Indicates whether the event is active, meaning it hasn't occurred yet.
     */
    private final boolean isActive;

    // Constructor
    /**
     * Constructs a new Event with the provided name, description, and date.
     * The event is marked as active if the date is after the current date.
     *
     * @param name The name of the event.
     * @param description The description of the event.
     * @param date The date of the event.
     */
    public Event(String name, String description, Date date) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.availableSeats = new ArrayList<>();
        this.isActive = !date.before(new Date());
        this.eventFeedbacks = new ArrayList<>();
    }

    // Getters
    /**
     * Gets the name of the event.
     *
     * @return The name of the event.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the event.
     *
     * @return The description of the event.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the date of the event.
     *
     * @return The date of the event.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the list of available seats for the event.
     *
     * @return A list of available seats.
     */
    public List<String> getAvailableSeats() {
        return availableSeats;
    }

    /**
     * Checks if the event is active (hasn't occurred yet).
     *
     * @return {@code true} if the event is active, {@code false} otherwise.
     */
    public boolean isActive() {
        return isActive;
    }

    public List<EventFeedback> getFeedbacks() {
        return eventFeedbacks;
    }

    // Overridden Methods
    /**
     * Compares two Event objects to see if they are equal, based on name, description, date, available seats, and active state.
     *
     * @param o The object to be compared.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return isActive == event.isActive &&
                Objects.equals(name, event.name) &&
                Objects.equals(description, event.description) &&
                Objects.equals(date, event.date) &&
                Objects.equals(availableSeats, event.availableSeats);
    }

    /**
     * Returns the hash code for the Event object, based on the attributes name, description, date, available seats, and active state.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, description, date, availableSeats, isActive);
    }

    /**
     * Returns a string representation of the Event object.
     *
     * @return A string containing the event details.
     */
    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", availableSeats=" + availableSeats +
                ", isActive=" + isActive +
                '}';
    }

    // Class Methods
    /**
     * Adds a seat to the list of available seats, if it is not already present.
     *
     * @param seat The seat to be added.
     */
    public void addSeat(String seat) {
        if (!availableSeats.contains(seat)) {
            this.availableSeats.add(seat);
        }
    }

    /**
     * Removes a seat from the list of available seats, if it is present.
     *
     * @param seat The seat to be removed.
     */
    public void removeSeat(String seat) {
        availableSeats.remove(seat);
    }
}
