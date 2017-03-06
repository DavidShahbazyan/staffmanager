package arm.davsoft.staffmanager.service;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by david on 8/8/16.
 */
public class LoginServiceTest {
    @Test
    public void checkLoginData() throws Exception {
        assertFalse(LoginService.checkLoginData("1", "1"));
        assertTrue(LoginService.checkLoginData("admin", "admin123!"));
    }

}