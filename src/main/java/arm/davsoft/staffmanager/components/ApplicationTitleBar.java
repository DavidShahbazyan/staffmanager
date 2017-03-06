package arm.davsoft.staffmanager.components;

import arm.davsoft.staffmanager.utils.ResourceManager;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * @author David Shahbazyan
 * @since Nov 27, 2015
 */
public class ApplicationTitleBar extends ToolBar {
    private Stage stage;
    private WindowButtons windowButtons;
    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;
    private boolean allowMinimise;
    private boolean allowMaximise;
    private boolean allowClose;
    /**
     * Creates an HBox layout with spacing = 0.
     */
    public ApplicationTitleBar(final Stage stage) {
        this(stage, true, true, true);
    }

    public ApplicationTitleBar(final Stage stage, boolean allowMinimise, boolean allowMaximise, boolean allowClose) {
        this.stage = stage;
        this.allowMinimise = allowMinimise;
        this.allowMaximise = allowMaximise;
        this.allowClose = allowClose;
        this.windowButtons = new WindowButtons(stage, allowMinimise, allowMaximise, allowClose);

        setId("mainToolBar");

        Image stageIcon = stage.getIcons().get(0);
        ImageView appLogo= new ImageView(stageIcon);
        appLogo.setFitHeight(20);
        appLogo.setFitWidth(appLogo.getFitHeight() * (stageIcon.getWidth()/stageIcon.getHeight()));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label titleLabel = new Label(stage.getTitle());
        titleLabel.getStyleClass().add("titleText");

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        getItems().addAll(appLogo, spacer, titleLabel, spacer1, this.windowButtons);
        initEvents();
    }

    private void initEvents() {
        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && allowMaximise) {
                windowButtons.toggleMaximized();
            }
        });
        // add window dragging
        this.setOnMousePressed(event -> {
            mouseDragOffsetX = event.getSceneX();
            mouseDragOffsetY = event.getSceneY();
        });
        this.setOnMouseDragged(event -> {
            if (!windowButtons.isMaximized()) {
                stage.setX(event.getScreenX() - mouseDragOffsetX);
                stage.setY(event.getScreenY() - mouseDragOffsetY);
            }
        });
    }

    public WindowButtons getWindowButtons() {
        return windowButtons;
    }
}
