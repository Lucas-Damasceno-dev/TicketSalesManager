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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Calendar;

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Ticket;

public class TicketTest {

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
    public void testCreateTicket() {
        Ticket ticket = new Ticket(futureEvent, 150.0F, "B12");

        assertNotNull(ticket);
        assertEquals(futureEvent, ticket.getEvent());
        assertEquals(150.0, ticket.getPrice(), 0.0001);
        assertEquals("B12", ticket.getSeat());
        assertTrue(ticket.isActive(), "New ticket should be active by default");
    }

    @Test
    public void testCancelTicketFutureEvent() {
        Ticket ticket = new Ticket(futureEvent, 150.0f, "C3");

        assertTrue(ticket.cancel(), "Should cancel a ticket for a future event");
        assertFalse(ticket.isActive(), "Cancelled ticket should be inactive");
    }

    @Test
    public void testCancelTicketPastEvent() {
        Ticket ticket = new Ticket(pastEvent, 100.0f, "A1");

        assertFalse(ticket.cancel(), "Should not cancel a ticket for a past event");
        assertTrue(ticket.isActive(), "Ticket for a past event should remain active");
    }

    @Test
    public void testReactivateTicket() {
        Ticket ticket = new Ticket(futureEvent, 100.0f, "D5");

        ticket.cancel();
        assertFalse(ticket.isActive(), "Ticket should be inactive after cancellation");

        ticket.reactivate();
        assertTrue(ticket.isActive(), "Ticket should be active after reactivation");
    }

    @Test
    public void testTicketDuplicate() {
        Ticket ticket1 = new Ticket(futureEvent, 150.0f, "E8");
        Ticket ticket2 = new Ticket(futureEvent, 150.0f, "E8");

        assertEquals(ticket1, ticket2, "Tickets with the same attributes should be equal");
        assertEquals(ticket1.hashCode(), ticket2.hashCode(), "Hash codes of equal tickets should match");
    }

    @Test
    public void testTicketNotEqualDifferentSeat() {
        Ticket ticket1 = new Ticket(futureEvent, 150.0f, "E8");
        Ticket ticket2 = new Ticket(futureEvent, 150.0f, "E9");

        assertNotEquals(ticket1, ticket2, "Tickets with different seats should not be equal");
    }

    @Test
    public void testTicketNotEqualDifferentEvent() {
        Event anotherEvent = new Event("Another Show", "Another Band", futureEvent.getDate());
        Ticket ticket1 = new Ticket(futureEvent, 150.0f, "F3");
        Ticket ticket2 = new Ticket(anotherEvent, 150.0f, "F3");

        assertNotEquals(ticket1, ticket2, "Tickets for different events should not be equal");
    }

    @Test
    public void testCancelAfterReactivate() {
        Ticket ticket = new Ticket(futureEvent, 200.0f, "G10");

        assertTrue(ticket.cancel(), "First cancellation should be successful");
        assertFalse(ticket.isActive(), "Ticket should be inactive after cancellation");

        ticket.reactivate();
        assertTrue(ticket.isActive(), "Ticket should be active after reactivation");

        assertTrue(ticket.cancel(), "Should be able to cancel again after reactivation");
        assertFalse(ticket.isActive(), "Ticket should be inactive after second cancellation");
    }
}

