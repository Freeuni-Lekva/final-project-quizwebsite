import junit.framework.TestCase;
import user.Hash;
import user.User;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.SortedMap;

public class TestUser extends TestCase {

    public void test1() throws NoSuchAlgorithmException {
        Hash hash = new Hash();
        String hashedPassword = hash.hashPassword("password");
        User user = new User("username", hashedPassword, 3, false, "Aleksandre", "Naneishvili");
        assertEquals("username", user.getUsername());
        assertEquals(hashedPassword, user.getPassword());
        assertEquals("Aleksandre Naneishvili", user.getFullName());
        assertEquals(3, user.getId());
        assertFalse(user.isAdmin());
        assertEquals( "User{" +
                "username='" + "username" + '\'' +
                ", password='" + hashedPassword + '\'' +
                ", id=" + 3 +
                ", isAdmin=" + false +
                ", firstName='" + "Aleksandre" + '\'' +
                ", lastName='" + "Naneishvili" + '\'' +
                '}', user.toString());
    }
}
