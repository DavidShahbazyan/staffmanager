package other;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author David Shahbazyan
 * @since Sep 10, 2016
 */
public class MD5 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update("admin123!".getBytes());
        System.out.println(new BigInteger(1, digest.digest()).toString(16));
    }
}
