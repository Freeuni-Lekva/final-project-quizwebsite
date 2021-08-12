package user;

import junit.framework.TestCase;
import user.Hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestHash extends TestCase {
    public void test1() throws NoSuchAlgorithmException {
        String password = "password";
        Hash hash = new Hash(password);
        String ans = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(password.getBytes(StandardCharsets.UTF_8));
            ans = hexToString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assertEquals(ans, hash.hashPassword());
    }

    private static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }
}
