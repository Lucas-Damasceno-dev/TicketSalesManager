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
        // Configura eventos futuros e passados para reuso nos testes
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
        assertTrue(ticket.isActive(), "Novo ticket deve ser ativo por padrão");
    }

    @Test
    public void testCancelTicketFutureEvent() {
        Ticket ticket = new Ticket(futureEvent, 150.0f, "C3");

        assertTrue(ticket.cancel(), "Deve cancelar um ticket para evento futuro");
        assertFalse(ticket.isActive(), "Ticket cancelado deve ficar inativo");
    }

    @Test
    public void testCancelTicketPastEvent() {
        Ticket ticket = new Ticket(pastEvent, 100.0f, "A1");

        assertFalse(ticket.cancel(), "Não deve cancelar ticket de evento passado");
        assertTrue(ticket.isActive(), "Ticket de evento passado deve continuar ativo");
    }

    @Test
    public void testReactivateTicket() {
        Ticket ticket = new Ticket(futureEvent, 100.0f, "D5");

        ticket.cancel();
        assertFalse(ticket.isActive(), "Ticket deve estar inativo após cancelamento");

        ticket.reactivate();
        assertTrue(ticket.isActive(), "Ticket deve estar ativo após reativação");
    }

    @Test
    public void testTicketDuplicate() {
        Ticket ticket1 = new Ticket(futureEvent, 150.0f, "E8");
        Ticket ticket2 = new Ticket(futureEvent, 150.0f, "E8");

        assertEquals(ticket1, ticket2, "Tickets com mesmos atributos devem ser iguais");
        assertEquals(ticket1.hashCode(), ticket2.hashCode(), "Hash codes de tickets iguais devem coincidir");
    }

    @Test
    public void testTicketNotEqualDifferentSeat() {
        Ticket ticket1 = new Ticket(futureEvent, 150.0f, "E8");
        Ticket ticket2 = new Ticket(futureEvent, 150.0f, "E9");

        assertNotEquals(ticket1, ticket2, "Tickets com assentos diferentes não devem ser iguais");
    }

    @Test
    public void testTicketNotEqualDifferentEvent() {
        Event anotherEvent = new Event("Another Show", "Another Band", futureEvent.getDate());
        Ticket ticket1 = new Ticket(futureEvent, 150.0f, "F3");
        Ticket ticket2 = new Ticket(anotherEvent, 150.0f, "F3");

        assertNotEquals(ticket1, ticket2, "Tickets de eventos diferentes não devem ser iguais");
    }

    @Test
    public void testCancelAfterReactivate() {
        Ticket ticket = new Ticket(futureEvent, 200.0f, "G10");

        assertTrue(ticket.cancel(), "Primeiro cancelamento deve ser bem-sucedido");
        assertFalse(ticket.isActive(), "Ticket deve estar inativo após cancelamento");

        ticket.reactivate();
        assertTrue(ticket.isActive(), "Ticket deve estar ativo após reativação");

        assertTrue(ticket.cancel(), "Deve ser possível cancelar novamente após reativação");
        assertFalse(ticket.isActive(), "Ticket deve estar inativo após segundo cancelamento");
    }
}
