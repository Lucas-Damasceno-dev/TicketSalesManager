import java.util.Date;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.lucas.ticketsalesmanager.controllers.Controller;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.models.User;

import static org.junit.jupiter.api.Assertions.*;


public class ControllerTest {

    @Test
    public void testRegisterEventByAdmin() {
        Controller controller = new Controller();
        User admin = controller.registerUser("admin", "password123", "Admin User",
                "00000000000", "admin@example.com", true);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date date = calendar.getTime();

        Event event = controller.registerEvent(admin, "Rock show", "Band XYZ", date);

        assertNotNull(event);
        assertEquals("Rock show", event.getName());
        assertEquals("Band XYZ", event.getDescription());
        assertEquals(date, event.getDate());
    }

    @Test
    public void testRegisterEventByCommonUser() {
        Controller controller = new Controller();
        User usuario = controller.registerUser("johndoe", "password123", "John Doe",
                "12345678901", "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date date = calendar.getTime();

        Exception exception = assertThrows(SecurityException.class, () -> {
            controller.registerEvent(usuario, "Peça de Teatro", "Grupo ABC", date);
        });

        assertEquals("Only administrators can register events.", exception.getMessage());
    }

    @Test
    public void testBuyTickets() {
        Controller controller = new Controller();
        User usuario = new User("johndoe", "password123", "John Doe", "12345678901",
                "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.SEPTEMBER, 10);
        Date date = calendar.getTime();

        User admin = controller.registerUser("admin", "password123", "Admin User",
                "00000000000", "admin@example.com", true);
        controller.registerEvent(admin, "Rock show", "Band XYZ", date);
        controller.addEventSeat("Rock show", "A1");

        Ticket ingresso = controller.purchaseTicket(usuario, "Rock show", "A1");

        assertNotNull(ingresso);
        assertEquals("Rock show", ingresso.getEvent().getName());
        assertEquals("A1", ingresso.getSeat());
        assertTrue(usuario.getTickets().contains(ingresso));
    }

    @Test
    public void testCancelPurchase() {
        Controller controller = new Controller();
        User usuario = new User("johndoe", "password123", "John Doe", "12345678901",
                "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.SEPTEMBER, 10);
        Date date = calendar.getTime();

        User admin = controller.registerUser("admin", "password123", "Admin User",
                "00000000000", "admin@example.com", true);
        controller.registerEvent(admin, "Rock show", "Band XYZ", date);
        controller.addEventSeat("Rock show", "A1");
        Ticket ingresso = controller.purchaseTicket(usuario, "Rock show", "A1");

        boolean cancelado = controller.cancelPurchase(usuario, ingresso);
        assertTrue(cancelado);
        assertFalse(ingresso.isActive());
        assertFalse(usuario.getTickets().contains(ingresso));
    }

    @Test
    public void testListAvailableEvents() {
        Controller controller = new Controller();
        User admin = controller.registerUser("admin", "password123", "Admin User",
                "00000000000", "admin@example.com", true);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2025, Calendar.SEPTEMBER, 10);
        Date date1 = calendar1.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2025, Calendar.SEPTEMBER, 15);
        Date date2 = calendar2.getTime();

        controller.registerEvent(admin, "Rock show", "Band XYZ", date1);
        controller.registerEvent(admin, "Peça de Teatro", "Grupo ABC", date2);

        List<Event> events = controller.listAvailableEvents();

        assertEquals(2, events.size());
    }

    @Test
    public void testListPurchasedTickets() {
        Controller controller = new Controller();
        User usuario = new User("johndoe", "password123", "John Doe", "12345678901",
                "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date date = calendar.getTime();

        User admin = controller.registerUser("admin", "password123", "Admin User",
                "00000000000", "admin@example.com", true);
        controller.registerEvent(admin, "Rock show", "Band XYZ", date);
        controller.addEventSeat("Rock show", "A1");
        controller.purchaseTicket(usuario, "Rock show", "A1");

        List<Ticket> tickets = controller.listPurchasedTickets(usuario);

        assertEquals(1, tickets.size());
    }
}
