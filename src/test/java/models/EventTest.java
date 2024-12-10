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

package models;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.service.communication.EventFeedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    private Event futureEvent;
    private Event pastEvent;

    @BeforeEach
    public void setUp() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(2025, Calendar.DECEMBER, 15);
        futureEvent = new Event("Future Event", "Future Band", calendar.getTime());

        calendar.set(2023, Calendar.JANUARY, 10);
        pastEvent = new Event("Past Event", "Old Band", calendar.getTime());
    }

    @Test
    public void testCreateEvent() {
        assertNotNull(futureEvent);
        assertEquals("Future Event", futureEvent.getName());
        assertEquals("Future Band", futureEvent.getDescription());
        assertEquals(futureEvent.getDate(), futureEvent.getDate());
        assertTrue(futureEvent.isActive(), "Future event should be active");
    }

    @Test
    public void testAddSeat() {
        futureEvent.addSeat("A1");
        assertTrue(futureEvent.getAvailableSeats().contains("A1"));
    }

    @Test
    public void testAddDuplicateSeat() {
        futureEvent.addSeat("A1");
        futureEvent.addSeat("A1");

        assertEquals(1, futureEvent.getAvailableSeats().size(), "Duplicate seat should not be added");
    }

    @Test
    public void testRemoveSeat() {
        futureEvent.addSeat("A1");
        futureEvent.removeSeat("A1");

        assertFalse(futureEvent.getAvailableSeats().contains("A1"), "Removed seat should not be in the list");
    }

    @Test
    public void testRemoveNonExistentSeat() {
        futureEvent.removeSeat("A1");
        assertFalse(futureEvent.getAvailableSeats().contains("A1"), "Removing a non-existent seat should not cause an error");
    }

    @Test
    public void testActiveEvent() {
        assertTrue(futureEvent.isActive(), "Future event should be active");
    }

    @Test
    public void testEventEquality() {
        Event eventDuplicate = new Event("Future Event", "Future Band", futureEvent.getDate());

        assertEquals(futureEvent, eventDuplicate, "Events with the same attributes should be equal");
        assertEquals(futureEvent.hashCode(), eventDuplicate.hashCode(), "Hash codes of equal events should match");
    }

    @Test
    public void testEventInequalityDifferentName() {
        Event differentEvent = new Event("Different Event", "Future Band", futureEvent.getDate());

        assertNotEquals(futureEvent, differentEvent, "Events with different names should not be equal");
    }

    @Test
    public void testEventInequalityDifferentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.NOVEMBER, 15);
        Date newDate = calendar.getTime();

        Event differentDateEvent = new Event("Future Event", "Future Band", newDate);

        assertNotEquals(futureEvent, differentDateEvent, "Events with different dates should not be equal");
    }

    @Test
    public void testEventFeedbackListEmptyByDefault() {
        assertNotNull(futureEvent.getFeedbacks(), "Feedback list should be initialized");
        assertTrue(futureEvent.getFeedbacks().isEmpty(), "Feedback list should be empty by default");
    }

    @Test
    public void testAddFeedback() {
        User user = new User("johndoe", "password123", "John Doe", "12345678901", "john.doe@example.com", false);

        Event event0 = new Event("Concert", "Music Concert", new Date());
        Event event1 = new Event("Theater", "Play", new Date());

        EventFeedback feedback1 = new EventFeedback(user, event0, 5, "Amazing show!");
        EventFeedback feedback2 = new EventFeedback(user, event1, 4, "Great play!");

        futureEvent.getFeedbacks().add(feedback1);
        futureEvent.getFeedbacks().add(feedback2);

        assertEquals(2, futureEvent.getFeedbacks().size(), "Feedback list should contain two feedbacks");
        assertTrue(futureEvent.getFeedbacks().contains(feedback1), "Feedback1 added should be in the list");
        assertTrue(futureEvent.getFeedbacks().contains(feedback2), "Feedback2 added should be in the list");
    }
}

