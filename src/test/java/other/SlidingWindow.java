package other;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.WritableValue;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class SlidingWindow extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("Main");
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);


        WritableValue<Double> writableValue = new WritableValue<Double>() {
            @Override
            public Double getValue() {
                return stage.getX();
            }

            @Override
            public void setValue(Double value) {
                stage.setX(value);
            }
        };

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenRightEdge = primScreenBounds.getMaxX();
        stage.setX(screenRightEdge);
        System.out.println(primScreenBounds.getWidth());
        stage.setWidth(300);
        stage.setHeight(100);
        stage.setX(primScreenBounds.getMaxX());
        stage.setY(primScreenBounds.getMaxY() - stage.getHeight());

        Timeline timeline = new Timeline();

        Button button = new Button();
        button.setOnAction(event -> {
            Timeline timeline1 = new Timeline();
            timeline1.getKeyFrames().addAll(new KeyFrame(Duration.millis(250), new KeyValue(writableValue, screenRightEdge - stage.getWidth()*1.5, Interpolator.EASE_BOTH)),
                    new KeyFrame(Duration.millis(1000), new KeyValue(writableValue, screenRightEdge + stage.getWidth(), Interpolator.EASE_BOTH)));
            timeline1.setOnFinished(e -> Platform.runLater(() -> stage.hide()));
            timeline1.play();
        });
        root.getChildren().add(button);

        timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(500), new KeyValue(writableValue, screenRightEdge - stage.getWidth(), Interpolator.EASE_BOTH)));
        timeline.play();
        stage.show();
//        stage.setOnCloseRequest(event -> {
//            Timeline timeline1 = new Timeline();
//            timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(3000), new KeyValue(writableValue, screenRightEdge, Interpolator.EASE_BOTH)));
//            timeline1.setOnFinished(e -> Platform.runLater(() -> stage.hide()));
//            timeline1.play();
//            event.consume();
//        });

    }
}