package controllers;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

import com.lucas.ticketsalesmanager.exception.event.*;
import com.lucas.ticketsalesmanager.exception.payment.PaymentException;
import com.lucas.ticketsalesmanager.exception.purchase.PurchaseException;
import com.lucas.ticketsalesmanager.exception.ticket.TicketAlreadyCanceledException;
import com.lucas.ticketsalesmanager.exception.ticket.TicketNotCancelableException;
import com.lucas.ticketsalesmanager.exception.ticket.TicketNotFoundException;
import com.lucas.ticketsalesmanager.exception.user.UserAlreadyExistsException;
import com.lucas.ticketsalesmanager.exception.user.UserDAOException;
import com.lucas.ticketsalesmanager.exception.user.UserNotFoundException;
import com.lucas.ticketsalesmanager.exception.ticket.TicketAlreadyExistsException;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Purchase;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.models.paymentMethod.Payment;
import com.lucas.ticketsalesmanager.models.paymentMethod.Pix;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lucas.ticketsalesmanager.controllers.UserController;
import com.lucas.ticketsalesmanager.controllers.TicketController;
import com.lucas.ticketsalesmanager.controllers.EventController;
import com.lucas.ticketsalesmanager.controllers.PurchaseController;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private UserController userController;
    private EventController eventController;
    private TicketController ticketController;
    private PurchaseController purchaseController;

    private Date date;
    private User admin;
    private User userCommon;
    private Event event;
    private Event registeredEvent;
    private Ticket registeredTicket;
    private Purchase registeredPurchase;
    private Pix eopix;

    @BeforeEach
    public void setUp() throws UserNotFoundException, UserAlreadyExistsException, UserDAOException, InvalidEventDateException, EventAlreadyExistsException {
        userController = new UserController();
        eventController = new EventController();
        ticketController = new TicketController();
        purchaseController = new PurchaseController();

        date = getDate(2025, Calendar.SEPTEMBER, 10);
        admin = userController.registerUser("admin", "password123", "Admin User", "00000000000", "admin@example.com", true);
        userCommon = userController.registerUser("johndoe", "password123", "John Doe", "12345678901", "john.doe@example.com", false);
        event = new Event("Concert", "BACH presentation", date);
        registeredEvent = eventController.registerEvent(admin, event);
        eopix = new Pix("123456789");
    }

    // Tests for EventController
    @Test
    public void testRegisterEventByAdmin() throws InvalidEventDateException, EventAlreadyExistsException {
        assertNotNull(registeredEvent);
        assertEquals("Concert", registeredEvent.getName());
        assertEquals("BACH presentation", registeredEvent.getDescription());
        assertEquals(date, registeredEvent.getDate());
    }

    @Test
    public void testRegisterEventByCommonUser() {


        Exception exception = assertThrows(SecurityException.class, () -> {
            eventController.registerEvent(userCommon, event);
        });

        assertEquals("Only administrators can register events.", exception.getMessage());
    }

    @Test
    public void testAddAndRemoveEventSeat() throws EventNotFoundException, InvalidEventDateException, EventAlreadyExistsException, SeatUnavailableException, EventUpdateException {
        eventController.addEventSeat(registeredEvent.getName(), "A1");

        List<String> seats = event.getAvailableSeats();
        assertTrue(seats.contains("A1"));

        eventController.removeEventSeat(event.getName(), "A1");
        assertFalse(seats.contains("A1"));
    }

    @Test
    public void testListAvailableEvents() {

        List<Event> events = eventController.listAvailableEvents();

        assertEquals(1, events.size());
    }

    // Tests for TicketController
    @Test
    public void testPurchaseTicket() throws TicketAlreadyExistsException, PurchaseException, SeatUnavailableException, EventUpdateException, EventNotFoundException, MessagingException {

        eventController.addEventSeat(event.getName(), "A1");

        Ticket ticket = ticketController.purchaseTicket(userCommon, event, 100.0f, "A1", eopix);

        assertNotNull(ticket);
        assertEquals("Concert", ticket.getEvent().getName());
        assertEquals("A1", ticket.getSeat());
        assertTrue(userCommon.getTickets().contains(ticket));
    }

    @Test
    public void testCancelTicket() throws EventNotFoundException, PurchaseException, SeatUnavailableException, EventUpdateException, TicketAlreadyExistsException, MessagingException, TicketAlreadyCanceledException, TicketNotFoundException, TicketNotCancelableException {

        eventController.addEventSeat(event.getName(), "A1");

        Ticket ticket = ticketController.purchaseTicket(userCommon, event, 100.0f, "A1", eopix);

        boolean cancelled = ticketController.cancelTicket(admin, ticket);

        assertTrue(cancelled);
        assertFalse(ticket.isActive());
        assertFalse(userCommon.getTickets().contains(ticket));
    }

    // Tests for PurchaseController
    @Test
    public void testProcessPurchase() throws PurchaseException, TicketAlreadyExistsException, SeatUnavailableException, EventUpdateException, EventNotFoundException, MessagingException, PaymentException {

        eventController.addEventSeat(event.getName(), "A1");
        Ticket ticket = ticketController.purchaseTicket(userCommon, event, 100.0f, "A1", eopix);

        purchaseController.processPurchase(userCommon, ticket, eopix);

        assertTrue(userCommon.getTickets().contains(ticket));
    }

    @Test
    public void testCancelPurchase() throws PurchaseException, SeatUnavailableException, EventUpdateException, EventNotFoundException, TicketAlreadyExistsException, MessagingException, PaymentException {

        eventController.addEventSeat(event.getName(), "A1");
        Ticket ticket = ticketController.purchaseTicket(userCommon, event, 100.0f, "A1", eopix);

        Purchase purchaseProcessed = purchaseController.processPurchase(userCommon, ticket, eopix);

        purchaseController.cancelPurchase(purchaseProcessed);

        assertFalse(ticket.isActive());
        assertFalse(userCommon.getTickets().contains(ticket));
    }

    // Utility method for date creation
    private Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
