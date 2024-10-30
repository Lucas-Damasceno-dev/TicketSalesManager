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

import com.lucas.ticketsalesmanager.models.Event;
import com.lucas.ticketsalesmanager.models.User;
import com.lucas.ticketsalesmanager.models.Ticket;
import com.lucas.ticketsalesmanager.service.communication.EventFeedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;
    private User admin;

    @BeforeEach
    public void setUp() {
        user = new User("johndoe", "password123", "John Doe", "12345678901", "john.doe@example.com", false);
        admin = new User("admin", "password123", "Admin User", "00000000000", "admin@example.com", true);
    }

    @Test
    public void testRegisterUser() {
        assertNotNull(user);
        assertEquals("johndoe", user.getLogin());
        assertEquals("John Doe", user.getName());
        assertEquals("12345678901", user.getCpf());
        assertEquals("john.doe@example.com", user.getEmail());
        assertFalse(user.isAdmin());
    }

    @Test
    public void testRegisterUserAdmin() {
        assertNotNull(admin);
        assertEquals("admin", admin.getLogin());
        assertEquals("Admin User", admin.getName());
        assertEquals("00000000000", admin.getCpf());
        assertEquals("admin@example.com", admin.getEmail());
        assertTrue(admin.isAdmin());
    }

    @Test
    public void testLogin() {
        assertTrue(user.login("johndoe", "password123"));
        assertFalse(user.login("johndoe", "wrongPassword"));
    }

    @Test
    public void testUpdatePassword() {
        user.setPassword("newPassword123");
        assertTrue(user.login("johndoe", "newPassword123"));
        assertFalse(user.login("johndoe", "password123"));
    }

    @Test
    public void testUpdateUserData() {
        user.setName("Jonathan Doe");
        user.setCpf("10987654321");
        user.setEmail("jon.doe@example.com");

        assertEquals("Jonathan Doe", user.getName());
        assertEquals("10987654321", user.getCpf());
        assertEquals("jon.doe@example.com", user.getEmail());
    }

    @Test
    public void testUserDuplicate() {
        User duplicateUser = new User("johndoe", "password456", "John Doe", "12345678901", "john.doe@example.com", false);
        assertEquals(user, duplicateUser);
    }

    @Test
    public void testTickets() {
        Ticket ticket1 = new Ticket(new Event("Concert", "Music Concert", new Date()), 50.0f, "A1");
        Ticket ticket2 = new Ticket(new Event("Theater", "Play", new Date()), 40.0f, "B2");

        user.getTickets().add(ticket1);
        user.getTickets().add(ticket2);

        assertEquals(2, user.getTickets().size());
        assertTrue(user.getTickets().contains(ticket1));
        assertTrue(user.getTickets().contains(ticket2));
    }

    @Test
    public void testAddEventFeedback() {
        User user = new User("johndoe", "password123", "John Doe", "12345678901", "john.doe@example.com", false);

        Event event0 = new Event("Concert", "Music Concert", new Date());
        Event event1 = new Event("Theater", "Play", new Date());

        EventFeedback feedback1 = new EventFeedback(user,event0, 5, "Amazing show!");
        EventFeedback feedback2 = new EventFeedback(user, event1, 4, "Great play!");

        user.getFeedback().add(feedback1);
        user.getFeedback().add(feedback2);

        assertEquals(2, user.getFeedback().size());
        assertEquals(feedback1, user.getFeedback().get(0));
        assertEquals(feedback2, user.getFeedback().get(1));
    }

    @Test
    public void testEqualsAndHashCode() {
        User duplicateUser = new User("johndoe", "password456", "John Doe", "12345678901", "john.doe@example.com", false);
        assertEquals(user, duplicateUser);
        assertEquals(user.hashCode(), duplicateUser.hashCode());
    }

    @Test
    public void testToString() {
        String userString = user.toString();
        assertTrue(userString.contains("johndoe"));
        assertTrue(userString.contains("John Doe"));
        assertTrue(userString.contains("12345678901"));
        assertTrue(userString.contains("john.doe@example.com"));
        assertTrue(userString.contains("isAdmin=false"));
    }
}
