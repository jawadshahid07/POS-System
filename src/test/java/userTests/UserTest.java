package userTests;

import business.userAuth.Manager;
import business.userAuth.Role;
import business.userAuth.User;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testLoginWithCorrectCredentials() {
        User user = new User("staff", "staff");

        assertEquals(0, user.login("staff", "staff"));
    }

    @Test
    public void testLoginWithIncorrectPassword() {
        User user = new User("testUser", "password");

        assertEquals(2, user.login("staff", "wrongPassword"));
    }

    @Test
    public void testLoginWithNonexistentUsername() {
        User user = new User("testUser", "password");

        assertEquals(1, user.login("nonexistentUser", "password"));
    }
}
