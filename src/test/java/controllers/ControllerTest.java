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
package controllers;

import java.nio.file.*;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import com.lucas.ticketsalesmanager.controllers.EventController;
import com.lucas.ticketsalesmanager.controllers.PurchaseController;
import com.lucas.ticketsalesmanager.controllers.TicketController;
import com.lucas.ticketsalesmanager.controllers.UserController;
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

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private static final UserController userController = new UserController();
    private static final EventController eventController = new EventController();
    private static final TicketController ticketController = new TicketController();
    private static final PurchaseController purchaseController = new PurchaseController();

    private static Date date;
    private static User admin;
    private static User userCommon;
    private static Event registeredEvent;
    private static Pix eopix;

    @BeforeEach
    void setUpInitialDataBeforeEach() throws InvalidEventDateException, UserNotAuthorizedException, EventNotFoundException, UserNotFoundException, UserDAOException {
        // Configuração inicial para o evento e usuários
        date = getDate();

        // Cria o usuário admin e o usuário comum, se não existir
        admin = createOrFetchUser("admin", "password123", "Admin User", "00000000000", "admin@example.com", true);
        userCommon = createOrFetchUser("johndoe", "password123", "John Doe", "12345678901", "john.doe@example.com", false);

        // Configuração inicial do evento
        registeredEvent = createOrFetchEvent(admin, "Concert", "BACH presentation", date);

        // Instancia o metodo de pagamento Pix
        eopix = new Pix("123456789");

        // Verifica se as instâncias foram corretamente criadas
        assertNotNull(admin);
        assertNotNull(userCommon);
        assertNotNull(registeredEvent);
    }

    @AfterEach
    void cleanUpJsonFiles() {
        try {
            Path projectRoot = Paths.get("").toAbsolutePath();
            Files.list(projectRoot)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (Exception e) {
                            System.err.println("Erro ao deletar o arquivo: " + path + " - " + e.getMessage());
                        }
                    });
        } catch (Exception e) {
            System.err.println("Erro ao listar arquivos no diretório raiz: " + e.getMessage());
        }
    }

    @Test
    void testRegisterEventByAdmin() {
        assertNotNull(registeredEvent);
        assertEquals("Concert", registeredEvent.getName());
        assertEquals("BACH presentation", registeredEvent.getDescription());
    }

    @Test
    void testRegisterEventByCommonUser() {
        assertNotNull(userCommon);
        UserNotAuthorizedException exception = assertThrows(UserNotAuthorizedException.class,
                () -> eventController.registerEvent(userCommon, registeredEvent));

        assertEquals("Only administrators can register events.", exception.getUserMessage());
        assertEquals("The user " + userCommon.getName() + " is not an admin!", exception.getMessage());
    }

    @Test
    void testAddAndRemoveEventSeat() throws EventNotFoundException, SeatUnavailableException, EventUpdateException {
        Event event;
        try {
            event = eventController.addEventSeat(registeredEvent.getName(), "A1");
        } catch (SeatUnavailableException e) {
            event = eventController.getEventByName(registeredEvent.getName());
        }

        assertTrue(event.getAvailableSeats().contains("A1"));
        assertEquals(1, event.getAvailableSeats().size());

        Event event0 = eventController.removeEventSeat(event.getName(), "A1");
        assertFalse(event0.getAvailableSeats().contains("A1"));
        assertEquals(0, event0.getAvailableSeats().size());
    }

    @Test
    void testListAvailableEvents() {
        List<Event> events = eventController.listAvailableEvents();
        assertFalse(events.isEmpty());
    }

    @Test
    void testCancelTicket() throws EventNotFoundException, PurchaseException, SeatUnavailableException,
            EventUpdateException, TicketAlreadyExistsException, MessagingException, TicketAlreadyCanceledException,
            TicketNotFoundException, TicketNotCancelableException {
        Event event = eventController.addEventSeat(registeredEvent.getName(), "A3");
        Ticket ticket = ticketController.purchaseTicket(userCommon, event, 100.0f, "A3", eopix);
        boolean cancelled = ticketController.cancelTicket(admin, ticket);
        assertTrue(cancelled);
        assertFalse(ticket.isActive());
        assertFalse(userCommon.getTickets().contains(ticket));
    }

    @Test
    void testProcessPurchase() throws PurchaseException, TicketAlreadyExistsException, MessagingException, PaymentException {
        Ticket ticket = ticketController.purchaseTicket(userCommon, registeredEvent, 100.0f, "A4", eopix);
        purchaseController.processPurchase(userCommon, ticket, eopix);
        assertTrue(userCommon.getTickets().contains(ticket));
    }

    private Date getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.SEPTEMBER, 10);
        return calendar.getTime();
    }

    private User createOrFetchUser(String login, String password, String name, String cpf, String email, boolean isAdmin) throws UserNotFoundException, UserDAOException {
        try {
            return userController.registerUser(login, password, name, cpf, email, isAdmin);
        } catch (UserAlreadyExistsException e) {
            return userController.findUserByLogin(login);
        }
    }

    private Event createOrFetchEvent(User user, String name, String description, Date date) throws InvalidEventDateException, EventNotFoundException, UserNotAuthorizedException {
        try {
            Event event = new Event(name, description, date);
            return eventController.registerEvent(user, event);
        } catch (EventAlreadyExistsException e) {
            return eventController.getEventByName(name);
        }
    }
}
