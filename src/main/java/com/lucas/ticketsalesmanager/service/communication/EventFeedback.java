package com.lucas.ticketsalesmanager.service.communication;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.User;

import java.util.Objects;

public class EventFeedback {
    // Attributes
    private User user;
    private Event event;
    private float rating;
    private String comment;

    // Constructor
    public EventFeedback(User user, Event event, float rating, String comment) {
        this.user = user;
        this.event = event;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // Overridden Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventFeedback eventFeedback = (EventFeedback) o;
        return Float.compare(rating, eventFeedback.rating) == 0 && Objects.equals(user, eventFeedback.user) && Objects.equals(event, eventFeedback.event) && Objects.equals(comment, eventFeedback.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, event, rating, comment);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "user=" + user +
                ", event=" + event +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }

    // Methods
    public static EventFeedback evaluateEvent(User user, Event event, float rating, String comment) {
        if ((rating < 0.0f || rating > 5.0f) && comment.isEmpty()) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0 or your comment is empty");
        }
        return new EventFeedback(user, event, rating, comment);
    }
}