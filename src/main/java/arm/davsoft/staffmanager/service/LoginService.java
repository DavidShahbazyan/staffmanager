package arm.davsoft.staffmanager.service;

import arm.davsoft.staffmanager.helpers.ConnectionProvider;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by david on 8/4/16.
 */
public final class LoginService {
    private static final String LOGIN = "SELECT * FROM UI_Users WHERE username=? AND password=? AND active=1;";

    private LoginService() { /* DO NOTHING */ }

    public static boolean checkLoginData(@Nonnull String username, @Nonnull String password) throws SQLException, NoSuchAlgorithmException {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        username = username.trim();
        password = password.trim();

        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(password.getBytes());
        password = (new BigInteger(1, digest.digest()).toString(16));

        boolean retVal;
        PreparedStatement pst = ConnectionProvider.getInstance().getConnection().prepareStatement(LOGIN);
        pst.setString(1, username);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();
        retVal = rs.next();
        rs.close();
        pst.close();
        return retVal;
    }

}
