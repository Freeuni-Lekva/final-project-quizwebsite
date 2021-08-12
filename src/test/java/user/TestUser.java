package user;

import junit.framework.TestCase;
import user.Hash;
import user.User;

import java.security.NoSuchAlgorithmException;

public class TestUser extends TestCase {

    public void test1() throws NoSuchAlgorithmException {
        Hash hash = new Hash("password");
        String hashedPassword = hash.hashPassword();
        User user = new User(3, "username", hashedPassword, false, "Aleksandre", "Naneishvili");
        assertEquals("username", user.getUsername());
        assertEquals(hashedPassword, user.getPassword());
        assertEquals(3, user.getId());
        assertFalse(user.isAdmin());
        assertEquals( "User{" +
                "username='" + "username" + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", id=" + 3 +
                ", isAdmin=" + false +
                ", firstName='" + "Aleksandre" + '\'' +
                ", lastName='" + "Naneishvili" + '\'' +
                '}', user.toString());
    }
}
