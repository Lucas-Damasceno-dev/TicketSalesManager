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
        assertTrue(futureEvent.isActive(), "Evento futuro deve estar ativo");
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

        assertEquals(1, futureEvent.getAvailableSeats().size(), "Assento duplicado não deve ser adicionado");
    }

    @Test
    public void testRemoveSeat() {
        futureEvent.addSeat("A1");
        futureEvent.removeSeat("A1");

        assertFalse(futureEvent.getAvailableSeats().contains("A1"), "Assento removido não deve estar na lista");
    }

    @Test
    public void testRemoveNonExistentSeat() {
        futureEvent.removeSeat("A1");
        assertFalse(futureEvent.getAvailableSeats().contains("A1"), "Remover assento não existente não deve gerar erro");
    }

    @Test
    public void testActiveEvent() {
        assertTrue(futureEvent.isActive(), "Evento futuro deve estar ativo");
    }

    @Test
    public void testInactiveEvent() {
        assertFalse(pastEvent.isActive(), "Evento passado deve estar inativo");
    }

    @Test
    public void testEventEquality() {
        Event eventDuplicate = new Event("Future Event", "Future Band", futureEvent.getDate());

        assertEquals(futureEvent, eventDuplicate, "Eventos com mesmos atributos devem ser iguais");
        assertEquals(futureEvent.hashCode(), eventDuplicate.hashCode(), "Hash codes de eventos iguais devem coincidir");
    }

    @Test
    public void testEventInequalityDifferentName() {
        Event differentEvent = new Event("Different Event", "Future Band", futureEvent.getDate());

        assertNotEquals(futureEvent, differentEvent, "Eventos com nomes diferentes não devem ser iguais");
    }

    @Test
    public void testEventInequalityDifferentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.NOVEMBER, 15);
        Date newDate = calendar.getTime();

        Event differentDateEvent = new Event("Future Event", "Future Band", newDate);

        assertNotEquals(futureEvent, differentDateEvent, "Eventos com datas diferentes não devem ser iguais");
    }

    @Test
    public void testEventFeedbackListEmptyByDefault() {
        assertNotNull(futureEvent.getFeedbacks(), "Lista de feedbacks deve ser inicializada");
        assertTrue(futureEvent.getFeedbacks().isEmpty(), "Lista de feedbacks deve estar vazia por padrão");
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

        assertEquals(2, futureEvent.getFeedbacks().size(), "Lista de feedbacks deve conter dois feedbacks");
        assertTrue(futureEvent.getFeedbacks().contains(feedback1), "Feedback1 adicionado deve estar na lista");
        assertTrue(futureEvent.getFeedbacks().contains(feedback2), "Feedback2 adicionado deve estar na lista");
    }
}
