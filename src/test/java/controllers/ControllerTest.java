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
import com.lucas.ticketsalesmanager.exception.user.*;
import com.lucas.ticketsalesmanager.exception.ticket.TicketAlreadyExistsException;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Purchase;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.models.paymentMethod.Pix;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lucas.ticketsalesmanager.controllers.UserController;
import com.lucas.ticketsalesmanager.controllers.TicketController;
import com.lucas.ticketsalesmanager.controllers.EventController;
import com.lucas.ticketsalesmanager.controllers.PurchaseController;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private UserController userController;
    private EventController eventController;
    private TicketController ticketController;
    private PurchaseController purchaseController;

    private Date date;
    private User admin;
    private User userCommon;
    private Event event;
    private Event registeredEvent;
    private Pix eopix;

    @BeforeEach
    public void setUp() throws UserAlreadyExistsException, UserDAOException, InvalidEventDateException, EventAlreadyExistsException, UserNotFoundException, EventNotFoundException, UserNotAuthorizedException {
        userController = new UserController();
        eventController = new EventController();
        ticketController = new TicketController();
        purchaseController = new PurchaseController();

        date = getDate();
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        admin = userController.registerUser("admin" + uniqueSuffix, "password123", "Admin User", "00000000000", "admin@example.com", true);
        userCommon = userController.registerUser("johndoe" + uniqueSuffix, "password123", "John Doe", "12345678901", "john.doe@example.com", false);

        // Verifica se o evento já está registrado antes de tentar registrá-lo
        try {
            event = new Event("Concert", "BACH presentation", date);
            registeredEvent = eventController.registerEvent(admin, event);
            registeredEvent.addSeat("A1");  // Adiciona o assento "A1" aqui
        } catch (EventAlreadyExistsException e) {
            registeredEvent = eventController.getEventByName("Concert");
            registeredEvent.addSeat("A1");  // Certifica-se que "A1" está disponível também ao pegar um evento existente
        }

        eopix = new Pix("123456789");
    }



    @AfterEach
    public void tearDown() {
        try {
            if (userCommon != null) {
                userController.removeUser(userCommon.getName());
            }
        } catch (UserNotFoundException | UserRemovalException e) {
            // Log ou ignore
        }

        try {
            if (admin != null) {
                userController.removeUser(admin.getName());
            }
        } catch (UserNotFoundException | UserRemovalException e) {
            // Log ou ignore
        }

        try {
            if (registeredEvent != null) {
                eventController.removeEvent(registeredEvent.getName());
            }
        } catch (EventNotFoundException e) {
            // Log ou ignore
        }
    }

    // Tests for EventController
    @Test
    void testRegisterEventByAdmin() {
        assertNotNull(registeredEvent);
        assertEquals("Concert", registeredEvent.getName());
        assertEquals("BACH presentation", registeredEvent.getDescription());
        assertEquals(date, registeredEvent.getDate());
    }

    @Test
    void testRegisterEventByCommonUser() {

        UserNotAuthorizedException exception = assertThrows(UserNotAuthorizedException.class, () -> eventController.registerEvent(userCommon, event));

        assertEquals("Only administrators can register events.", exception.getUserMessage());

        String expectedDetailMessage = "The user " + userCommon.getName() + " is not an admin!";
        assertEquals(expectedDetailMessage, exception.getMessage());
    }




    @Test
    void testAddAndRemoveEventSeat() throws EventNotFoundException, SeatUnavailableException, EventUpdateException {
        // O assento A1 já deve estar disponível por causa do setUp.
        eventController.addEventSeat(registeredEvent.getName(), "A1");
        assertTrue(registeredEvent.getAvailableSeats().contains("A1"));

        assertEquals(1, registeredEvent.getAvailableSeats().size());

        eventController.removeEventSeat(registeredEvent.getName(), "A1");
        assertFalse(registeredEvent.getAvailableSeats().contains("A1"));

        assertEquals(0, registeredEvent.getAvailableSeats().size());
    }



    @Test
    void testListAvailableEvents() {

        List<Event> events = eventController.listAvailableEvents();

        assertEquals(1, events.size());
    }

    // Tests for TicketController
    @Test
    void testPurchaseTicket() throws TicketAlreadyExistsException, PurchaseException, SeatUnavailableException, EventUpdateException, EventNotFoundException, MessagingException {

        eventController.addEventSeat(event.getName(), "A1");

        Ticket ticket = ticketController.purchaseTicket(userCommon, event, 100.0f, "A1", eopix);

        assertNotNull(ticket);
        assertEquals("Concert", ticket.getEvent().getName());
        assertEquals("A1", ticket.getSeat());
        assertTrue(userCommon.getTickets().contains(ticket));
    }

    @Test
    void testCancelTicket() throws EventNotFoundException, PurchaseException, SeatUnavailableException, EventUpdateException, TicketAlreadyExistsException, MessagingException, TicketAlreadyCanceledException, TicketNotFoundException, TicketNotCancelableException {

        eventController.addEventSeat(event.getName(), "A1");

        Ticket ticket = ticketController.purchaseTicket(userCommon, event, 100.0f, "A1", eopix);

        boolean cancelled = ticketController.cancelTicket(admin, ticket);

        assertTrue(cancelled);
        assertFalse(ticket.isActive());
        assertFalse(userCommon.getTickets().contains(ticket));
    }

    // Tests for PurchaseController
    @Test
    void testProcessPurchase() throws PurchaseException, TicketAlreadyExistsException, SeatUnavailableException, EventUpdateException, EventNotFoundException, MessagingException, PaymentException {

        eventController.addEventSeat(event.getName(), "A1");
        Ticket ticket = ticketController.purchaseTicket(userCommon, event, 100.0f, "A1", eopix);

        purchaseController.processPurchase(userCommon, ticket, eopix);

        assertTrue(userCommon.getTickets().contains(ticket));
    }

    @Test
    void testCancelPurchase() throws PurchaseException, SeatUnavailableException, EventUpdateException, EventNotFoundException, TicketAlreadyExistsException, MessagingException, PaymentException {

        eventController.addEventSeat(event.getName(), "A1");
        Ticket ticket = ticketController.purchaseTicket(userCommon, event, 100.0f, "A1", eopix);

        Purchase purchaseProcessed = purchaseController.processPurchase(userCommon, ticket, eopix);

        purchaseController.cancelPurchase(purchaseProcessed);

        assertFalse(ticket.isActive());
        assertFalse(userCommon.getTickets().contains(ticket));
    }

    // Utility method for date creation
    private Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.SEPTEMBER, 10);
        return calendar.getTime();
    }
}
