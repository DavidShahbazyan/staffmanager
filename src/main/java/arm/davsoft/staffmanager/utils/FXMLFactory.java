package arm.davsoft.staffmanager.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * <b>Author:</b> David Shahbazyan <br/>
 * <b>Date:</b> 8/19/15 <br/>
 * <b>Time:</b> 12:01 AM <br/>
 */
public final class FXMLFactory {
    private static final String FXML_DIR_NAME = "/screens/";

    public static Parent getFXMLParent(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setResources(ResourceManager.getBundle("properties/messages"));
//        fxmlLoader.setResources(ResourceBundle.getBundle("properties/messages", Locale.ENGLISH));
//        fxmlLoader.setResources(ResourceBundle.getBundle("properties/messages", Locale.CHINESE));
        return fxmlLoader.load(FXMLFactory.class.getResourceAsStream(FXML_DIR_NAME + fxmlFile));
    }
}
