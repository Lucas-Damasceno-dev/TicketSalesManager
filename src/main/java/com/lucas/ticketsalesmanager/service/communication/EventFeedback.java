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

package com.lucas.ticketsalesmanager.service.communication;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.User;

import java.util.Objects;

/**
 * The EventFeedback class represents a user's feedback for a specific event.
 * It contains the user's rating and comment regarding the event.
 */
public class EventFeedback {

    private User user;
    private Event event;
    private float rating;
    private String comment;

    /**
     * Constructs an EventFeedback instance with the specified user, event, rating, and comment.
     *
     * @param user    The user providing the feedback.
     * @param event   The event being evaluated.
     * @param rating  The rating given to the event, between 0.0 and 5.0.
     * @param comment The comment accompanying the rating.
     */
    public EventFeedback(User user, Event event, float rating, String comment) {
        this.user = user;
        this.event = event;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and Setters

    /**
     * Gets the user who provided the feedback.
     *
     * @return The user associated with the feedback.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who provided the feedback.
     *
     * @param user The user to associate with the feedback.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the event that the feedback is related to.
     *
     * @return The event associated with the feedback.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the event that the feedback is related to.
     *
     * @param event The event to associate with the feedback.
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Gets the rating provided in the feedback.
     *
     * @return The rating value.
     */
    public float getRating() {
        return rating;
    }

    /**
     * Sets the rating for the feedback.
     *
     * @param rating The new rating value.
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Gets the comment provided in the feedback.
     *
     * @return The feedback comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment for the feedback.
     *
     * @param comment The new feedback comment.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

// Overridden Methods

    /**
     * Checks the equality between two EventFeedback objects.
     *
     * @param o The object to be compared.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventFeedback eventFeedback = (EventFeedback) o;
        return Float.compare(rating, eventFeedback.rating) == 0 &&
                Objects.equals(user, eventFeedback.user) &&
                Objects.equals(event, eventFeedback.event) &&
                Objects.equals(comment, eventFeedback.comment);
    }

    /**
     * Returns the hash code based on the feedback attributes.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(user, event, rating, comment);
    }

    /**
     * Returns a textual representation of the EventFeedback object.
     *
     * @return A string containing the user's feedback details.
     */
    @Override
    public String toString() {
        return "Feedback{" +
                "user=" + user +
                ", event=" + event +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }


    /**
     * Evaluates an event and creates an EventFeedback instance.
     *
     * @param user    The user providing feedback.
     * @param event   The event being evaluated.
     * @param rating  The rating for the event.
     * @param comment The comment for the event.
     * @return A new EventFeedback instance.
     * @throws IllegalArgumentException if the rating is out of range (0.0 - 5.0) or if the comment is empty.
     */
    public static EventFeedback evaluateEvent(User user, Event event, float rating, String comment) {
        if ((rating < 0.0f || rating > 5.0f) && comment.isEmpty()) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0 or your comment is empty");
        }
        return new EventFeedback(user, event, rating, comment);
    }
}
