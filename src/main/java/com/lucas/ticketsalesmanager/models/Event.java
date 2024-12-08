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
    private String name;

    /**
     * The description of the event.
     */
    private String description;

    /**
     * The date on which the event will take place.
     */
    private Date date;

    /**
     * The list of available seats for the event.
     */
    private final ArrayList<String> availableSeats;

    /**
     * List of feedback for the event.
     */
    private final ArrayList<EventFeedback> eventFeedbacks;

    /**
     * Indicates whether the event is active, meaning it hasn't occurred yet.
     */
    private boolean isActive;

    // Constructor
    /**
     * Constructs a new Event with the provided name, description, and date.
     * The event is marked as active if the date is after the current date.
     *
     * @param name        The name of the event.
     * @param description The description of the event.
     * @param date        The date of the event.
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

    /**
     * Gets the list of feedback for the event.
     *
     * @return A list of event feedbacks.
     */
    public List<EventFeedback> getFeedbacks() {
        return eventFeedbacks;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do evento não pode ser vazio ou nulo.");
        }
        this.name = name;
    }

    /**
     * Atualiza a descrição do evento.
     *
     * @param description A nova descrição do evento.
     */
    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição do evento não pode ser vazia ou nula.");
        }
        this.description = description;
    }

    /**
     * Atualiza a data do evento.
     *
     * @param date A nova data do evento.
     */
    public void setDate(Date date) {
        if (date == null || date.before(new Date())) {
            throw new IllegalArgumentException("A data do evento deve ser futura e não pode ser nula.");
        }
        this.date = date;
        this.isActive = !date.before(new Date()); // Atualiza o status de 'ativo'
    }

    /**
     * Atualiza o status do evento.
     *
     * @param isActive Novo status do evento.
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Define uma nova lista de assentos disponíveis.
     *
     * @param newAvailableSeats Lista de novos assentos.
     */
    public void setAvailableSeats(List<String> newAvailableSeats) {
        if (newAvailableSeats == null) {
            throw new IllegalArgumentException("A lista de assentos não pode ser nula.");
        }
        availableSeats.clear();
        availableSeats.addAll(newAvailableSeats);
    }

    /**
     * Define uma nova lista de feedbacks do evento.
     *
     * @param newFeedbacks Lista de novos feedbacks.
     */
    public void setFeedbacks(List<EventFeedback> newFeedbacks) {
        if (newFeedbacks == null) {
            throw new IllegalArgumentException("A lista de feedbacks não pode ser nula.");
        }
        eventFeedbacks.clear();
        eventFeedbacks.addAll(newFeedbacks);
    }

    // Overridden Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return isActive == event.isActive &&
                Objects.equals(name, event.name) &&
                Objects.equals(description, event.description) &&
                Objects.equals(date, event.date) &&
                Objects.equals(availableSeats, event.availableSeats) &&
                Objects.equals(eventFeedbacks, event.eventFeedbacks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, date, availableSeats, eventFeedbacks, isActive);
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
     * Adds a seat to the list of available seats if it is not already present.
     *
     * @param seat The seat to be added.
     */
    public void addSeat(String seat) {
        if (!availableSeats.contains(seat)) {
            this.availableSeats.add(seat);
        }
    }

    /**
     * Removes a seat from the list of available seats if it is present.
     *
     * @param seat The seat to be removed.
     */
    public void removeSeat(String seat) {
        if (availableSeats.remove(seat)) {
            System.out.println("Seat removed: " + seat);
        } else {
            System.out.println("Seat not found: " + seat);
        }
    }
}
