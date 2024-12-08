package com.lucas.ticketsalesmanager.views.controllers.scenes.util;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

public class UserTickets {
    private final SimpleStringProperty eventName;
    private final SimpleObjectProperty<Date> eventDate;
    private final SimpleStringProperty eventSeat;

    public UserTickets(String eventName, Date eventDate, String eventSeat) {
        this.eventName = new SimpleStringProperty(eventName);
        this.eventDate = new SimpleObjectProperty<>(eventDate);
        this.eventSeat = new SimpleStringProperty(eventSeat);
    }

    public String getEventName() {
        return eventName.get();
    }

    public SimpleStringProperty eventNameProperty() {
        return eventName;
    }

    public Date getEventDate() {
        return eventDate.get();
    }

    public SimpleObjectProperty<Date> eventDateProperty() {
        return eventDate;
    }

    public String getEventSeat() {
        return eventSeat.get();
    }

    public SimpleStringProperty eventSeatProperty() {
        return eventSeat;
    }

    public void setEventName(String name) {
        this.eventName.set(name);
    }

    public void setEventDate(Date date) {
        this.eventDate.set(date);
    }

    public void setEventSeat(String seat) {
        this.eventSeat.set(seat);
    }
}
