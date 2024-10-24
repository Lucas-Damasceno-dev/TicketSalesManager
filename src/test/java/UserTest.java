import org.junit.jupiter.api.Test;
import com.lucas.ticketsalesmanager.models.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserTest {

    @Test
    public void testRegisterUser() {
        User user = new User("johndoe", "password123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertNotNull(user);
        assertEquals("johndoe", user.getLogin());
        assertEquals("John Doe", user.getName());
        assertEquals("12345678901", user.getCpf());
        assertEquals("john.doe@example.com", user.getEmail());
        assertFalse(user.isAdmin());
    }

    @Test
    public void testRegisterUserAdmin() {
        User admin = new User("admin", "password123", "Admin User", "00000000000", "admin@example.com", true);

        assertNotNull(admin);
        assertEquals("admin", admin.getLogin());
        assertEquals("Admin User", admin.getName());
        assertEquals("00000000000", admin.getCpf());
        assertEquals("admin@example.com", admin.getEmail());
        assertTrue(admin.isAdmin());
    }

    @Test
    public void testLogin() {
        User user = new User("johndoe", "password123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertTrue(user.login("johndoe", "password123"));
        assertFalse(user.login("johndoe", "passwordErrada"));
    }

    @Test
    public void testUpdatePassword() {
        User user = new User("johndoe", "password123", "John Doe", "12345678901", "john.doe@example.com", false);

        user.setPassword("novaSenha123");
        assertTrue(user.login("johndoe", "novaSenha123"));
        assertFalse(user.login("johndoe", "password123"));
    }

    @Test
    public void testDataUser() {
        User user = new User("johndoe", "password123", "John Doe", "12345678901", "john.doe@example.com", false);

        user.setName("Jonathan Doe");
        user.setCpf("10987654321");
        user.setEmail("jon.doe@example.com");

        assertEquals("Jonathan Doe", user.getName());
        assertEquals("10987654321", user.getCpf());
        assertEquals("jon.doe@example.com", user.getEmail());
    }

    @Test
    public void testUserDuplicate() {
        User user1 = new User("johndoe", "password123", "John Doe", "12345678901", "john.doe@example.com", false);
        User user2 = new User("johndoe", "password456", "John Doe", "12345678901", "john.doe@example.com", false);

        assertEquals(user1, user2);
    }
}
