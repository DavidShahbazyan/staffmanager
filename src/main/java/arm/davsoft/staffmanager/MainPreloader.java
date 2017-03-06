package arm.davsoft.staffmanager;

import arm.davsoft.staffmanager.utils.ResourceManager;
import javafx.application.Preloader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * <b>Author:</b> David Shahbazyan <br/>
 * <b>Date:</b> 8/25/15 <br/>
 * <b>Time:</b> 12:25 AM <br/>
 */
public class MainPreloader extends Preloader {
    ProgressBar bar;
    Stage stage;
    Label applicationName;
    Label applicationVersion;

    private Scene createPreloaderScene() {
        bar = new ProgressBar();
        bar.setPrefHeight(3);
        bar.getStyleClass().add("white");
//        bar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2)");

        ImageView splash = new ImageView("/images/splashScreen.png");
        splash.setFitHeight(250);
        splash.setFitWidth(splash.getFitHeight() * 2.097112861);

        AnchorPane p = new AnchorPane(splash, bar);

        AnchorPane.setTopAnchor(splash, (double) 0);
        AnchorPane.setRightAnchor(splash, (double) 0);
        AnchorPane.setBottomAnchor(splash, (double) 0);
        AnchorPane.setLeftAnchor(splash, (double) 0);

        AnchorPane.setRightAnchor(bar, (double) 100);
        AnchorPane.setBottomAnchor(bar, (double) 100);
        AnchorPane.setLeftAnchor(bar, (double) 100);

        p.setStyle("-fx-background-color: transparent;");
        return new Scene(p);
    }

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle(ResourceManager.getParam("APPLICATION.NAME"));
        stage.getIcons().add(ResourceManager.getAppLogo());
        stage.setScene(createPreloaderScene());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getScene().setFill(Color.TRANSPARENT);
        stage.getScene().getStylesheets().add("/css/style.css");
        stage.show();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        stage.requestFocus();
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification pn) {
        if (pn instanceof ProgressNotification) {
            bar.setProgress(((ProgressNotification) pn).getProgress());
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }
}
