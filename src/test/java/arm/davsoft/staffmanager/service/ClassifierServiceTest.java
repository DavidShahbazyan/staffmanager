package arm.davsoft.staffmanager.service;

import arm.davsoft.staffmanager.enums.MetaCategory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by david on 8/4/16.
 */
public class ClassifierServiceTest {
    @Test
    public void loadClassifierById() throws Exception {
        assertNull(ClassifierService.loadClassifierById(MetaCategory.GENDER, 0));
        assertNotNull(ClassifierService.loadClassifierById(MetaCategory.GENDER, 1));
    }

    @Test
    public void loadClassifiers() throws Exception {
        assertNotNull(ClassifierService.loadClassifiers(MetaCategory.GENDER));
        assertFalse(ClassifierService.loadClassifiers(MetaCategory.GENDER).isEmpty());
    }

}