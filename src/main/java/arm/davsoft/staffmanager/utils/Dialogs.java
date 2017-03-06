package arm.davsoft.staffmanager.utils;

import arm.davsoft.staffmanager.components.ProcessIndicator;
import arm.davsoft.staffmanager.utils.dialogs.AboutAppDialog;
import arm.davsoft.staffmanager.utils.dialogs.ExceptionDialog;
import arm.davsoft.staffmanager.utils.dialogs.SettingsDialog;
import javafx.animation.*;
import javafx.beans.value.WritableValue;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.*;
import javafx.util.Duration;
import org.apache.log4j.Logger;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.util.*;

/**
 * <b>Author:</b> David Shahbazyan <br/>
 * <b>Date:</b> 6/5/15 <br/>
 * <b>Time:</b> 10:25 PM <br/>
 */
public final class Dialogs {
    public enum NotificationType { NONE, WARNING, ERROR, INFO, CONFIRM, CUSTOM }

    private Dialogs() {
    }

    public static Window getParentWindow(Node node) {
        return node.getScene().getWindow();
    }

    private static void fireStageCloseRequest(Stage stage) {
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public static File openFileChooser(Window owner, String title, FileChooser.ExtensionFilter extensionFilter, File initialDirectory) {
        FileChooser fileChooser = new FileChooser();
        if (title != null) {
            fileChooser.setTitle(title);
        }
        if (extensionFilter != null) {
            fileChooser.setSelectedExtensionFilter(extensionFilter);
        }
        if (initialDirectory != null) {
            fileChooser.setInitialDirectory(initialDirectory);
        } else {
            fileChooser.setInitialDirectory(ResourceManager.getUserHomeDir());
        }
        return fileChooser.showOpenDialog(owner);
    }

    public static void showNotification(NotificationType type, String title, String content) {
        showNotification(type, title, content, null);
    }

    public static void showNotification(NotificationType type, String title, String content, Node graphic) {
        Notifications notificationBuilder = Notifications.create()
                .title(title != null ? title : "")
                .text(content)
                .graphic(graphic);
//                .hideAfter(Duration.seconds(5))
//                .position(Pos.BOTTOM_RIGHT)
//                .onAction(new EventHandler<ActionEvent>() {
//                    @Override public void handle(ActionEvent arg0) {
//                        System.out.println("Notification clicked on!");
//                    }
//                });
        switch (type) {
            case WARNING: notificationBuilder.showWarning(); break;
            case ERROR: notificationBuilder.showError(); break;
            case INFO: notificationBuilder.showInformation(); break;
            case CONFIRM: notificationBuilder.showConfirm(); break;
            default: notificationBuilder.show();
        }
    }
/*
    public static void showNotification(String title, String content, Image graphic) {
        final Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        VBox root = new VBox(5);

        stage.setWidth(400);
        stage.setHeight(150);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenRightEdge = primScreenBounds.getMaxX();
        double screenBottomEdge = primScreenBounds.getMaxY();

        WritableValue<Double> stageYValue = new WritableValue<Double>() {
            @Override
            public Double getValue() {
                return stage.getY();
            }

            @Override
            public void setValue(Double value) {
                stage.setY(value);
            }
        };

        stage.setTitle(title);
        stage.setOnCloseRequest(event -> {
            Timeline hideTimeline = new Timeline();
            hideTimeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(500), new KeyValue(stageYValue, screenBottomEdge, Interpolator.EASE_BOTH), new KeyValue(stage.opacityProperty(), 0, Interpolator.EASE_BOTH)));
            hideTimeline.setOnFinished(event1 -> stage.close());
            hideTimeline.play();
            event.consume();
        });
        Label lblTitle = new Label(title);
        HBox titleHBox = new HBox(lblTitle);
        HBox.setHgrow(lblTitle, Priority.ALWAYS);

        ImageView icon = new ImageView(new Image(Dialogs.class.getResourceAsStream("/images/icons/dialog/dialog-information.png")));
        Label lblContent = new Label(content);
        HBox contentHBox = new HBox(icon, lblContent);
        HBox.setHgrow(lblContent, Priority.ALWAYS);

        root.getChildren().addAll(titleHBox, contentHBox);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(ResourceManager.getUIThemeStyle());
        stage.setScene(scene);

        stage.setX(screenRightEdge - stage.getWidth());
        stage.setY(screenBottomEdge);
        stage.setOpacity(0);

        stage.show();
        Timeline showTimeline = new Timeline();
        showTimeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(500),
                        new KeyValue(stageYValue, screenBottomEdge - stage.getHeight(), Interpolator.EASE_BOTH),
                        new KeyValue(stage.opacityProperty(), 1, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.millis(5000), event -> fireStageCloseRequest(stage)));
//        showTimeline.setCycleCount(Animation.INDEFINITE);
        showTimeline.play();
    }
*/

    public static void showInfoPopup(String title, String content) {
        showInfoPopup(title, null, content);
    }

    public static void showInfoPopup(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    public static void showWarningPopup(String title, String content) {
        showWarningPopup(title, null, content);
    }

    public static void showWarningPopup(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    public static boolean showConfirmDialog(String title, String header, String content) {
        return showConfirmDialog(null, title, header, content);
    }

    public static boolean showConfirmDialog(Node graphic, String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.NO);
        if (graphic != null) {
            alert.setGraphic(graphic);
        }
        alert.setTitle(title);
        alert.setHeaderText(header);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.YES;
    }

    public static <T> T showChoicePopup(String title, String header, String content, List<T> choiceList) {
        T retVal = null;
        ChoiceDialog<T> dialog = new ChoiceDialog<>(choiceList.get(0), choiceList);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        Optional<T> result = dialog.showAndWait();
        if (!result.equals(Optional.<T>empty())) {
            retVal = result.get();
        }
        return retVal;
    }

    public static void showErrorPopup(String title, String content) {
        showErrorPopup(title, null, content);
    }

    public static void showErrorPopup(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
            if (title != null) {
                alert.setTitle(title);
            } else {
                alert.setTitle(ResourceManager.getMessage("title.dialog.error"));
            }
            alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showExceptionDialog(String header, String content, Throwable throwable) {
        ExceptionDialog.create(header, content, throwable).showAndWait();
    }

    public static void showTaskProgressDialog(Window ownerWindow, Task task, boolean showTaskMessage) {
        final Stage dialog = new Stage();
        task.setOnSucceeded(event -> dialog.close());
        task.setOnCancelled(event -> dialog.close());
        dialog.initStyle(StageStyle.UTILITY);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(ownerWindow);
        dialog.titleProperty().bind(task.titleProperty());
//        dialog.setTitle(ResourceManager.getMessage("title.dialog.processing"));
        dialog.setOnCloseRequest(event -> Logger.getLogger(Dialogs.class).info(ResourceManager.getMessage("notification.task.terminatedByUser")));

        ProgressBar progressBar = new ProgressBar(0);
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setMaxWidth(Double.MAX_VALUE);
        progressBar.getStyleClass().add("dark");

        Label label = new Label(ResourceManager.getMessage("label.pleaseWaitWhile"));
        Label taskMessage = new Label();
        taskMessage.textProperty().bind(task.messageProperty());

        Button cancelButton = new Button(ResourceManager.getMessage("label.button.cancel"));
        cancelButton.setOnAction(event -> {
            task.cancel();
            Logger.getLogger(Dialogs.class).info(ResourceManager.getMessage("notification.task.terminatedByUser"));
        });

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().add(cancelButton);

        VBox dialogVBox = new VBox();
        dialogVBox.setFillWidth(true);
        dialogVBox.setSpacing(5);
        dialogVBox.setPadding(new Insets(5));
        dialogVBox.setPrefSize(300, VBox.USE_COMPUTED_SIZE);
        dialogVBox.getChildren().add(label);
        if (showTaskMessage) {
            dialogVBox.getChildren().add(taskMessage);
        }
        dialogVBox.getChildren().add(progressBar);
        dialogVBox.getChildren().add(buttonBar);

        Scene dialogScene = new Scene(dialogVBox);
        dialogScene.getStylesheets().add(ResourceManager.getUIThemeStyle());
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.show();
    }

    public static void showWebBrowser(String url) {
        showWebBrowser(null, url);
    }

    public static void showWebBrowser(String title, String url) {
        final Stage dialog = new Stage();
        dialog.getIcons().add(new Image("images/icons/general/web.png"));
        dialog.setTitle(title);

        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();
        engine.load(url);

        TextField addressField = new TextField();
        addressField.textProperty().bind(engine.locationProperty());
        addressField.setEditable(false);

        VBox root = new VBox(addressField, browser);
        VBox.setVgrow(browser, Priority.ALWAYS);

        Scene dialogScene = new Scene(root);
        dialogScene.getStylesheets().add(ResourceManager.getUIThemeStyle());
        dialog.setScene(dialogScene);

        dialog.setMinHeight(700);
        dialog.setMinWidth(1050);

        dialog.show();
        dialog.requestFocus();
    }

    public static void showSettingsDialog(Window ownerWindow) {
        SettingsDialog.create(ownerWindow).show(true);
    }

    public static void showAboutAppDialog(Window ownerWindow) {
        AboutAppDialog.create(ownerWindow).show(true);
    }
}
