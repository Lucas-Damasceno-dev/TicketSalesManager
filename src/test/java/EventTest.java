import java.util.Date;
import java.util.List;
import java.util.Calendar;
import com.lucas.ticketsalesmanager.models.Event;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTest {

    @Test
    public void testCreateEvent() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date date = calendar.getTime();

        Event event = new Event("Rock show", "Band XYZ", date);

        assertNotNull(event);
        assertEquals("Rock show", event.getName());
        assertEquals("Band XYZ", event.getDescription());
        assertEquals(date, event.getDate());
    }

    @Test
    public void testAddSeat() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date date = calendar.getTime();

        Event event = new Event("Rock show", "Band XYZ", date);
        event.addSeat("A1");

        List<String> seats = event.getAvailableSeats();
        assertTrue(seats.contains("A1"));
    }

    @Test
    public void testRemoveSeat() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date date = calendar.getTime();

        Event event = new Event("Rock show", "Band XYZ", date);
        event.addSeat("A1");
        event.removeSeat("A1");

        List<String> seats = event.getAvailableSeats();
        assertFalse(seats.contains("A1"));
    }

    @Test
    public void testActiveEvent() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.SEPTEMBER, 10);
        Date date = calendar.getTime();

        Event event = new Event("Rock show", "Band XYZ", date);

        assertTrue(event.isActive());
    }

    @Test
    public void testInactiveEvent() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 10);
        Date date = calendar.getTime();

        Event event = new Event("Rock show", "Band XYZ", date);

        assertFalse(event.isActive());
    }
}
