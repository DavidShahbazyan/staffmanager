package arm.davsoft.staffmanager.helpers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by david on 8/4/16.
 */
public class ClassifierCacheTest {
    @Test
    public void getGender() throws Exception {
        assertNull(ClassifierCache.getInstance().getGender(0));
        assertNotNull(ClassifierCache.getInstance().getGender(1));
    }

    @Test
    public void getGenders() throws Exception {
        assertNotNull(ClassifierCache.getInstance().getGenders());
        assertFalse(ClassifierCache.getInstance().getGenders().isEmpty());
    }

}