package controllers;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

import com.lucas.ticketsalesmanager.exception.event.*;
import com.lucas.ticketsalesmanager.exception.payment.PaymentException;
import com.lucas.ticketsalesmanager.exception.purchase.PurchaseException;
import com.lucas.ticketsalesmanager.exception.ticket.*;
import com.lucas.ticketsalesmanager.exception.user.*;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Purchase;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.models.paymentMethod.Pix;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.*;

import com.lucas.ticketsalesmanager.controllers.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerTest {

    private static UserController userController = new UserController();
    private static EventController eventController = new EventController();
    private static TicketController ticketController = new TicketController();
    private static PurchaseController purchaseController = new PurchaseController();

    private static Date date;
    private static User admin;
    private static User userCommon;
    private static Event registeredEvent;
    private static Pix eopix;

    @Test
    @Order(1)
    void setUpInitialData() throws InvalidEventDateException, EventAlreadyExistsException, UserNotAuthorizedException, EventNotFoundException, UserNotFoundException, UserDAOException {
        // Configuração inicial para o evento e usuários
        date = getDate();

        // Cria o usuário admin e o usuário comum, se não existir
        admin = createOrFetchUser("admin", "password123", "Admin User", "00000000000", "admin@example.com", true);
        userCommon = createOrFetchUser("johndoe", "password123", "John Doe", "12345678901", "john.doe@example.com", false);

        // Configuração inicial do evento
        registeredEvent = createOrFetchEvent(admin,"Concert", "BACH presentation", date);

        // Instancia o metodo de pagamento Pix
        eopix = new Pix("123456789");

        // Verifica se as instâncias foram corretamente criadas
        assertNotNull(admin);
        assertNotNull(userCommon);
        assertNotNull(registeredEvent);
    }

    @Test
    @Order(2)
    void testRegisterEventByAdmin() {
        // Confirma o registro do evento com os detalhes corretos
        assertNotNull(registeredEvent);
        assertEquals("Concert", registeredEvent.getName());
        assertEquals("BACH presentation", registeredEvent.getDescription());
        assertEquals(date, registeredEvent.getDate());
    }

    @Test
    @Order(3)
    void testRegisterEventByCommonUser() {
        assertNotNull(userCommon);
        // Verifica a exceção ao tentar registrar o evento como um usuário não admin
        UserNotAuthorizedException exception = assertThrows(UserNotAuthorizedException.class,
                () -> eventController.registerEvent(userCommon, registeredEvent));

        assertEquals("Only administrators can register events.", exception.getUserMessage());
        assertEquals("The user " + userCommon.getName() + " is not an admin!", exception.getMessage());
    }

    @Test
    @Order(4)
    void testAddAndRemoveEventSeat() throws EventNotFoundException, SeatUnavailableException, EventUpdateException {
        if (registeredEvent.getAvailableSeats().isEmpty()) {
            eventController.addEventSeat(registeredEvent.getName(), "A1");
        }

        System.out.println("Os assentos disponiveis são:"+ registeredEvent.getAvailableSeats());

        assertTrue(registeredEvent.getAvailableSeats().contains("A1"));
        assertEquals(1, registeredEvent.getAvailableSeats().size());

        // Remove o assento e confirma a remoção
        eventController.removeEventSeat(registeredEvent.getName(), "A1");
        assertFalse(registeredEvent.getAvailableSeats().contains("A1"));
        assertEquals(0, registeredEvent.getAvailableSeats().size());
    }

    @Test
    @Order(5)
    void testListAvailableEvents() {
        List<Event> events = eventController.listAvailableEvents();
        assertTrue(events.contains(registeredEvent));
    }

    @Test
    @Order(6)
    void testPurchaseTicket() throws TicketAlreadyExistsException, PurchaseException,
            SeatUnavailableException, EventUpdateException, EventNotFoundException, MessagingException {
        // Adiciona o assento para o teste de compra
        if (!registeredEvent.getAvailableSeats().contains("A1")) {
            eventController.addEventSeat(registeredEvent.getName(), "A1");
        }

        // Realiza a compra do ingresso
        Ticket ticket = ticketController.purchaseTicket(userCommon, registeredEvent, 100.0f, "A1", eopix);

        assertNotNull(ticket);
        assertEquals("Concert", ticket.getEvent().getName());
        assertEquals("A1", ticket.getSeat());
        assertTrue(userCommon.getTickets().contains(ticket));
    }

    @Test
    @Order(7)
    void testCancelTicket() throws EventNotFoundException, PurchaseException, SeatUnavailableException,
            EventUpdateException, TicketAlreadyExistsException, MessagingException, TicketAlreadyCanceledException,
            TicketNotFoundException, TicketNotCancelableException {

        // Adiciona o assento e realiza a compra para preparar a remoção
        if (!registeredEvent.getAvailableSeats().contains("A1")) {
            eventController.addEventSeat(registeredEvent.getName(), "A1");
        }
        Ticket ticket = ticketController.purchaseTicket(userCommon, registeredEvent, 100.0f, "A1", eopix);

        // Cancela o ingresso e verifica o estado
        boolean cancelled = ticketController.cancelTicket(admin, ticket);
        assertTrue(cancelled);
        assertFalse(ticket.isActive());
        assertFalse(userCommon.getTickets().contains(ticket));
    }

    @Test
    @Order(8)
    void testProcessPurchase() throws PurchaseException, TicketAlreadyExistsException, SeatUnavailableException,
            EventUpdateException, EventNotFoundException, MessagingException, PaymentException {

        // Adiciona o assento e realiza a compra
        if (!registeredEvent.getAvailableSeats().contains("A1")) {
            eventController.addEventSeat(registeredEvent.getName(), "A1");
        }
        Ticket ticket = ticketController.purchaseTicket(userCommon, registeredEvent, 100.0f, "A1", eopix);

        // Processa a compra e verifica o estado
        purchaseController.processPurchase(userCommon, ticket, eopix);
        assertTrue(userCommon.getTickets().contains(ticket));
    }

    @Test
    @Order(9)
    void testCancelPurchase() throws PurchaseException, SeatUnavailableException, EventUpdateException,
            EventNotFoundException, TicketAlreadyExistsException, MessagingException, PaymentException {

        // Adiciona o assento e realiza a compra
        if (!registeredEvent.getAvailableSeats().contains("A1")) {
            eventController.addEventSeat(registeredEvent.getName(), "A1");
        }
        Ticket ticket = ticketController.purchaseTicket(userCommon, registeredEvent, 100.0f, "A1", eopix);

        // Processa e cancela a compra, verificando o estado
        Purchase purchaseProcessed = purchaseController.processPurchase(userCommon, ticket, eopix);
        purchaseController.cancelPurchase(purchaseProcessed);

        assertFalse(ticket.isActive());
        assertFalse(userCommon.getTickets().contains(ticket));
    }

    // Utilitário para criação de data
    private Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.SEPTEMBER, 10);
        return calendar.getTime();
    }

    // Métodos auxiliares para criação ou recuperação de usuários e eventos
    private User createOrFetchUser(String login, String password, String name, String cpf, String email, boolean isAdmin) throws UserNotFoundException, UserDAOException {
        try {
            return userController.registerUser(login, password, name, cpf, email, isAdmin);
        } catch (UserAlreadyExistsException e) {
            return userController.findUserByLogin(login);
        }
    }

    private Event createOrFetchEvent(User user,String name, String description, Date date) throws InvalidEventDateException, EventAlreadyExistsException, EventNotFoundException, UserNotAuthorizedException {
        try {
            Event event = new Event(name, description, date);
            return eventController.registerEvent(user, event);
        } catch (EventAlreadyExistsException e) {
            return eventController.getEventByName(name);
        }
    }
}
