package arm.davsoft.staffmanager.utils;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by david on 8/22/16.
 */
public class UtilsTest {
    @Test
//    @Ignore("concatStrings -> DOES NOT NEED TESTING, YET.")
    public void concatStrings() throws Exception {
        assertEquals("a, b, c", Utils.concatStrings(Arrays.asList("a", "b", "c")));
    }

    @Test
//    @Ignore("concatStrings1 -> DOES NOT NEED TESTING, YET.")
    public void concatStrings1() throws Exception {
        assertEquals("a-b-c", Utils.concatObjects(Arrays.asList("a", "b", "c"), "-"));
    }

    @Test
//    @Ignore("joinIntegers -> DOES NOT NEED TESTING, YET.")
    public void joinIntegers() throws Exception {
        assertEquals("1,2,3", Utils.joinIntegers(Arrays.asList(1, 2, 3)));
    }

    @Test
    @Ignore("toJsonArray -> DOES NOT NEED TESTING, YET.")
    public void toJsonArray() throws Exception {
        assertEquals("[\"a\",\"b\",\"c\"]", Utils.toJsonArray(Arrays.asList("a", "b", "c")));
    }

    @Test
    @Ignore("byteArrayToFile -> DOES NOT NEED TESTING, YET.")
    public void byteArrayToFile() throws Exception {
        String fileName = "/images/splashScreen.png";
        String fileExt = ".png";
        Utils.byteArrayToFile(Utils.inputStreamToByteArray(UtilsTest.class.getResourceAsStream(fileName)), fileExt);
        System.out.println("Exiting the application...");
    }

    @Test
    @Ignore("fileToByteArray -> DOES NOT NEED TESTING, YET.")
    public void fileToByteArray() throws Exception {

    }

    @Test
    @Ignore("inputStreamToByteArray -> DOES NOT NEED TESTING, YET.")
    public void inputStreamToByteArray() throws Exception {

    }

}