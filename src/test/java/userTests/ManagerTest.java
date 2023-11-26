package userTests;

import business.userAuth.Manager;
import business.userAuth.Role;
import org.junit.Test;

import static org.junit.Assert.*;

public class ManagerTest {

    @Test
    public void testPermissions() {
        Manager manager = new Manager();
        assertEquals("Manager", manager.permissions());
    }
}
