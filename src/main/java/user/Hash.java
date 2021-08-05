package user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    private String password;

    public Hash(String password) {
        this.password = password;
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

    public String hashPassword() throws NoSuchAlgorithmException {
        String ans = "";
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(password.getBytes(StandardCharsets.UTF_8));
        ans = hexToString(md.digest());
        return ans;
    }
}
