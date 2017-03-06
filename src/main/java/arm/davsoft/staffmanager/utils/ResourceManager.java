package arm.davsoft.staffmanager.utils;

import javafx.scene.image.Image;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * <b>Author:</b> David Shahbazyan <br/>
 * <b>Date:</b> 7/10/15 <br/>
 * <b>Time:</b> 1:15 AM <br/>
 */
public final class ResourceManager {
    public static final String USER_HOME_DIR_PATH = System.getProperty("user.home");
    public static final String APP_HOME_DIR_PATH = USER_HOME_DIR_PATH + File.separatorChar + ".staffManager" + File.separatorChar;
    public static final String CUSTOM_SETTINGS_RESOURCE_FILE_PATH = APP_HOME_DIR_PATH + "settings.cfg";

    private static final String DELIMITER = " ";
    private static final String MESSAGES_RESOURCE_FILE = "properties/messages.properties";
    private static final String PARAMS_RESOURCE_FILE = "properties/params.properties";
    private static final String DEFAULT_SETTINGS_RESOURCE_FILE = "/properties/settings.properties";

    private static File APP_TEMP_DIR = null;

    private static PropertiesConfiguration messagesResourceConfig = null;
    private static PropertiesConfiguration paramsResourceConfig = null;

    private static Properties defaultAppProps = null;
    private static Properties customAppProps = null;

    static {
        try {
            APP_TEMP_DIR = Files.createTempDirectory(Paths.get(System.getProperty("java.io.tmpdir") + File.separatorChar), "staff-manager-").toFile();
            APP_TEMP_DIR.deleteOnExit();

            messagesResourceConfig = new PropertiesConfiguration(MESSAGES_RESOURCE_FILE);
            messagesResourceConfig.setEncoding("UTF-8");

            paramsResourceConfig = new PropertiesConfiguration(PARAMS_RESOURCE_FILE);
            paramsResourceConfig.setEncoding("UTF-8");

            defaultAppProps = new Properties();
            InputStream dIS = ResourceManager.class.getResourceAsStream(DEFAULT_SETTINGS_RESOURCE_FILE);
            defaultAppProps.load(dIS);
            dIS.close();

            customAppProps = new Properties(defaultAppProps);
            if (Files.exists(Paths.get(CUSTOM_SETTINGS_RESOURCE_FILE_PATH))) {
                InputStream cIS = new FileInputStream(CUSTOM_SETTINGS_RESOURCE_FILE_PATH);
                customAppProps.load(cIS);
                cIS.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(ResourceManager.class).error(ex);
        }
    }

    private ResourceManager() {}

    public static ResourceBundle getBundle(String baseName) {
        return ResourceBundle.getBundle(baseName);
    }

    public static List<Object> getParamList(String paramId) {
        return paramsResourceConfig.getList(paramId);
    }

    public static String getMessage(String... messageIds) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < messageIds.length; i++) {
            stringBuilder.append(messagesResourceConfig.getString(messageIds[i]));
            if (i != messageIds.length - 1) {
                stringBuilder.append(DELIMITER);
            }
        }
        return stringBuilder.toString();
    }

    public static File getUserHomeDir() { return new File(USER_HOME_DIR_PATH); }
    public static File getAppTempDir() { return APP_TEMP_DIR; }
    public static String getParam(String paramName) { return paramsResourceConfig.getString(paramName); }
    public static String getSetting(String settingName) { return customAppProps.getProperty(settingName); }
    public static Properties getSettings() { return customAppProps; }
    public static URL getResource(String resourcePath) { return ResourceManager.class.getResource(resourcePath); }
    public static Image getAppLogo() { return new Image("images/appLogo.png"); }
    public static Image getDavSoftLogo() { return new Image("images/davsoftLogo.png"); }
    public static URL getScene(String sceneName) { return ResourceManager.class.getResource("/screens/" + sceneName); }
    public static String getLogFileName() { return APP_HOME_DIR_PATH + "logs" + File.separatorChar + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + ".smlog"; }
    public static String getUIThemeStyle() {
        return ResourceManager.getResource("/css/style.css").toExternalForm();//"css/style.css";
    }
}
