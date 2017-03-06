package arm.davsoft.staffmanager.skin;

import arm.davsoft.staffmanager.components.ProcessIndicator;
import arm.davsoft.staffmanager.components.StatusBar;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * <b>Author:</b> David Shahbazyan <br/>
 * <b>Date:</b> 1/26/16 <br/>
 * <b>Time:</b> 11:22 PM <br/>
 */
public class StatusBarSkin extends SkinBase<StatusBar> {

    private HBox leftBox;
    private HBox rightBox;
    private Label label;
    private ImageView cancelButton;
    private ProcessIndicator processIndicator;
    private ProgressBar progressBar;

    public StatusBarSkin(StatusBar statusBar) {
        super(statusBar);

        leftBox = new HBox();
        leftBox.getStyleClass().add("left-items");

        rightBox = new HBox();
        rightBox.getStyleClass().add("right-items");

        processIndicator = new ProcessIndicator("images/icons/process/fs/step_1.png", true);
        processIndicator.visibleProperty().bind(Bindings.notEqual(0, statusBar.progressProperty()).and(Bindings.notEqual(100, statusBar.progressProperty())));

        progressBar = new ProgressBar();
        progressBar.setMinWidth(200);
        progressBar.setMinHeight(10);
        progressBar.progressProperty().bind(statusBar.progressProperty());
        progressBar.visibleProperty().bind(Bindings.notEqual(0, statusBar.progressProperty()).and(Bindings.notEqual(100, statusBar.progressProperty())));
        progressBar.getStyleClass().add("dark");

        cancelButton = new ImageView("images/icons/process/stop.png");
        cancelButton.setOnMouseClicked(event -> statusBar.cancelTask());
        cancelButton.setOnMouseEntered(event -> cancelButton.setImage(new Image("images/icons/process/stopHovered.png")));
        cancelButton.setOnMouseExited(event -> cancelButton.setImage(new Image("images/icons/process/stop.png")));
        cancelButton.visibleProperty().bind(Bindings.notEqual(0, statusBar.progressProperty()).and(Bindings.notEqual(100, statusBar.progressProperty())));

        label = new Label();
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        label.textProperty().bind(statusBar.textProperty());
        label.graphicProperty().bind(statusBar.graphicProperty());
        label.getStyleClass().add("status-label");

        leftBox.getChildren().setAll(getSkinnable().getLeftItems());

        rightBox.getChildren().setAll(getSkinnable().getRightItems());

        statusBar.getLeftItems().addListener((Observable evt) -> leftBox.getChildren().setAll(getSkinnable().getLeftItems()));

        statusBar.getRightItems().addListener((Observable evt) -> rightBox.getChildren().setAll(getSkinnable().getRightItems()));

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5));

        GridPane.setFillHeight(leftBox, true);
        GridPane.setFillHeight(rightBox, true);
        GridPane.setFillHeight(processIndicator, true);
        GridPane.setFillHeight(label, true);
        GridPane.setFillHeight(progressBar, true);
        GridPane.setFillHeight(cancelButton, true);

        GridPane.setVgrow(leftBox, Priority.ALWAYS);
        GridPane.setVgrow(rightBox, Priority.ALWAYS);
        GridPane.setVgrow(processIndicator, Priority.ALWAYS);
        GridPane.setVgrow(label, Priority.ALWAYS);
        GridPane.setVgrow(progressBar, Priority.ALWAYS);
        GridPane.setVgrow(cancelButton, Priority.ALWAYS);

        GridPane.setHgrow(label, Priority.ALWAYS);

        gridPane.add(leftBox, 0, 0);
        gridPane.add(processIndicator, 1, 0);
        gridPane.add(label, 2, 0);
        gridPane.add(progressBar, 3, 0);
        gridPane.add(cancelButton, 4, 0);
        gridPane.add(rightBox, 5, 0);

        getChildren().add(gridPane);
    }
}
