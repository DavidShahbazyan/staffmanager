package arm.davsoft.staffmanager.components;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author David Shahbazyan
 * @since Nov 27, 2015
 */
public class WindowButtons extends HBox {
    private Stage stage;
    private Rectangle2D backupWindowBounds = null;
    private boolean maximized = false;
    private BooleanProperty minBtnVisible = new SimpleBooleanProperty(true);
    private BooleanProperty maxBtnVisible = new SimpleBooleanProperty(true);
    private BooleanProperty closeBtnVisible = new SimpleBooleanProperty(true);

    public WindowButtons(final Stage stage, boolean allowMinimise, boolean allowMaximise, boolean allowClose) {
        super(4);
        this.stage = stage;
        // create buttons
        Button closeBtn = new Button();
        closeBtn.setId("window_close");
        closeBtn.visibleProperty().bind(closeBtnVisible);
//        closeBtn.setOnAction(actionEvent -> Platform.exit());
        closeBtn.setOnAction(actionEvent -> stage.close());
        Button minBtn = new Button();
        minBtn.setId("window_min");
        minBtn.visibleProperty().bind(minBtnVisible);
        minBtn.setOnAction(actionEvent -> stage.setIconified(true));
        Button maxBtn = new Button();
        maxBtn.setId("window_max");
        maxBtn.visibleProperty().bind(maxBtnVisible);
        maxBtn.setOnAction(actionEvent -> toggleMaximized());
        if (allowMinimise) {
            getChildren().add(minBtn);
        }
        if (allowMaximise) {
            getChildren().add(maxBtn);
        }
        if (allowClose) {
            getChildren().add(closeBtn);
        }
    }

    public void toggleMaximized() {
        final Screen screen = Screen.getScreensForRectangle(stage.getX(), stage.getY(), 1, 1).get(0);
        if (maximized) {
            maximized = false;
            if (backupWindowBounds != null) {
                stage.setX(backupWindowBounds.getMinX());
                stage.setY(backupWindowBounds.getMinY());
                stage.setWidth(backupWindowBounds.getWidth());
                stage.setHeight(backupWindowBounds.getHeight());
            }
        } else {
            maximized = true;
            backupWindowBounds = new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
            stage.setX(screen.getVisualBounds().getMinX());
            stage.setY(screen.getVisualBounds().getMinY());
            stage.setWidth(screen.getVisualBounds().getWidth());
            stage.setHeight(screen.getVisualBounds().getHeight());
        }
    }

    public boolean isMaximized() {
        return maximized;
    }

    public boolean isMinBtnVisible() {
        return minBtnVisible.get();
    }
    public BooleanProperty minBtnVisiblePropertyProperty() {
        return minBtnVisible;
    }

    public boolean isMaxBtnVisible() {
        return maxBtnVisible.get();
    }
    public BooleanProperty maxBtnVisiblePropertyProperty() {
        return maxBtnVisible;
    }

    public boolean isCloseBtnVisible() {
        return closeBtnVisible.get();
    }
    public BooleanProperty closeBtnVisiblePropertyProperty() {
        return closeBtnVisible;
    }
}
