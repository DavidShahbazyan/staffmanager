package arm.davsoft.staffmanager;

import arm.davsoft.staffmanager.enums.ErrorCode;
import arm.davsoft.staffmanager.stages.CustomStage;
import arm.davsoft.staffmanager.stages.LoginStage;
import arm.davsoft.staffmanager.stages.MainStage;
import arm.davsoft.staffmanager.utils.AppSpecUncaughtExceptionHandler;
import arm.davsoft.staffmanager.utils.ResourceManager;
import com.sun.javafx.css.StyleManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by david on 7/19/16.
 */
public class Main extends Application {
    public static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        Thread.currentThread().setUncaughtExceptionHandler(new AppSpecUncaughtExceptionHandler());
        try {
            launch(args);
            LOGGER.info("---------------------- Ending the Application ----------------------");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void init() {
        try {
            setUserAgentStylesheet(STYLESHEET_MODENA);
            StyleManager.getInstance().addUserAgentStylesheet(ResourceManager.getUIThemeStyle());
            initLogger();
//            initClassifiersCache();
//            LOGGER.info("Application init completed!");
            LOGGER.info("--------------------- Starting the Application ---------------------");
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(10);
                notifyPreloader(new Preloader.ProgressNotification(0.01 * i));
            }
        } catch (Exception ex) {
            LOGGER.error("Error occurred in main init method: ", ex);
            Platform.exit();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread.currentThread().setUncaughtExceptionHandler(new AppSpecUncaughtExceptionHandler());
        primaryStage = new LoginStage();
//        primaryStage = new MainStage();
        ((CustomStage) primaryStage).showAndFocus();
    }

    private void initLogger() throws IOException {
        Properties props = new Properties();
        InputStream input = getClass().getResourceAsStream("/properties/log4j.properties");
        if (input == null) {
            throw new FileNotFoundException(ErrorCode.LOG4J_PROP_MISSING.getCode());
        }
        props.load(input);
        if (Boolean.valueOf(ResourceManager.getSetting("exportLogToFile"))) {
            props.setProperty("log4j.appender.fileAppender.File", ResourceManager.getLogFileName());
        }
        LogManager.resetConfiguration();
        PropertyConfigurator.configure(props);
//        LOGGER.info("Logging has been successfully initialized.");
    }

    public static void restart() {
//        System.exit(88);
        try {
            final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            final File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        /* is it a jar file? */
            if (!currentJar.getName().endsWith(".jar")) return;

        /* Build command: java -jar application.jar */
            final ArrayList<String> command = new ArrayList<>();
            command.add(javaBin);
            command.add("-jar");
            command.add(currentJar.getPath());

            final ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();

            LOGGER.info("Application is restarting.");
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        Platform.exit();
    }

    public static void exit() {
        LOGGER.info("Application terminated by user.");
        Platform.exit();
    }
}
