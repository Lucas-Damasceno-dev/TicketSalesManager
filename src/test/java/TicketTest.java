import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Calendar;
import java.util.Date;
import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.Ticket;

public class TicketTest {

    @Test
    public void testCreateTicket() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date date = calendar.getTime();
        
        Event event = new Event("Rock show", "Band XYZ", date);
        Ticket ticket = new Ticket(event, 100.0F, "A1");

        assertNotNull(ticket);
        assertEquals(event, ticket.getEvent());
        assertEquals(100.0, ticket.getPrice(), 0.0001);
        assertEquals("A1", ticket.getSeat());
        assertTrue(ticket.isActive());
    }

    @Test
    public void testCancelTicket() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.SEPTEMBER, 10);
        Date date = calendar.getTime();

        Event event = new Event("Rock show", "Band XYZ", date);
        Ticket ticket = new Ticket(event, 100.0f, "A1");

        assertTrue(ticket.cancel());
        assertFalse(ticket.isActive());
    }

    @Test
    public void testCancelTicketPastEvent() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 10);
        Date date = calendar.getTime();

        Event event = new Event("Rock show", "Band XYZ", date);
        Ticket ticket = new Ticket(event, 100.0f, "A1");

        assertFalse(ticket.cancel());
        assertTrue(ticket.isActive());
    }

    @Test
    public void testReactivateTicket() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.SEPTEMBER, 10);
        Date date = calendar.getTime();

        Event event = new Event("Rock show", "Band XYZ", date);
        Ticket ticket = new Ticket(event, 100.0f, "A1");

        ticket.cancel();
        assertFalse(ticket.isActive());

        ticket.reactivate();
        assertTrue(ticket.isActive());
    }

    @Test
    public void testTicketDuplicate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date date = calendar.getTime();

        Event event = new Event("Rock show", "Band XYZ", date);
        Ticket ticket1 = new Ticket(event, 100.0f, "A1");
        Ticket ticket2 = new Ticket(event, 100.0f, "A1");

        assertEquals(ticket1, ticket2);
        assertEquals(ticket1.hashCode(), ticket2.hashCode());
    }
}
