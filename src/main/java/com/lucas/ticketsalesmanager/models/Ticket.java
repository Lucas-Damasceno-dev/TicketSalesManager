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

import java.util.Date;
import java.util.Objects;

/**
 * The Ticket class represents a ticket for a specific event.
 * It contains information about the event, price, seat, and whether the ticket is active or not.
 */
public class Ticket {

    // Attributes
    /**
     * The event for which the ticket was purchased.
     */
    private final Event event;

    /**
     * The price of the ticket.
     */
    private final float price;

    /**
     * The seat assigned to the ticket.
     */
    private final String seat;

    /**
     * Indicates whether the ticket is active (valid) or not.
     */
    private boolean isActive;

    // Constructor
    /**
     * Constructs a new Ticket with the provided event, price, and seat.
     * The ticket is created as active by default.
     *
     * @param event The event associated with the ticket.
     * @param price The price of the ticket.
     * @param seat The designated seat.
     */
    public Ticket(Event event, float price, String seat) {
        this.event = event;
        this.price = price;
        this.seat = seat;
        this.isActive = true;
    }

    // Getters
    /**
     * Gets the event associated with the ticket.
     *
     * @return The ticket's event.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Gets the price of the ticket.
     *
     * @return The ticket's price.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Gets the seat assigned to the ticket.
     *
     * @return The ticket's seat.
     */
    public String getSeat() {
        return seat;
    }

    /**
     * Checks if the ticket is active.
     *
     * @return {@code true} if the ticket is active, {@code false} otherwise.
     */
    public boolean isActive() {
        return isActive;
    }

    // Overridden Methods
    /**
     * Compares two Ticket objects to see if they are equal, based on event, price, seat, and active status.
     *
     * @param o The object to be compared.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Float.compare(price, ticket.price) == 0 && isActive == ticket.isActive &&
                Objects.equals(event, ticket.event) && Objects.equals(seat, ticket.seat);
    }

    /**
     * Returns the hash code for the Ticket object, based on the attributes event, price, seat, and active status.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(event, price, seat, isActive);
    }

    /**
     * Returns a string representation of the Ticket object.
     *
     * @return A string containing the ticket details.
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "event=" + event +
                ", price=" + price +
                ", seat='" + seat + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    // Class Methods
    /**
     * Cancels the ticket if it is active and the event has not yet occurred.
     *
     * @return {@code true} if the ticket was successfully canceled, {@code false} otherwise.
     */
    public boolean cancel() {
        if (isActive && event.getDate().after(new Date())) {
            isActive = false;
            return true;
        }
        return false;
    }

    /**
     * Reactivates the ticket if it is inactive.
     */
    public void reactivate() {
        if (!isActive) {
            isActive = true;
        }
    }
}
