package other;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author David Shahbazyan
 * @since Sep 26, 2016
 */
public class aaa extends Application {
    ObservableList<String> entries = FXCollections.observableArrayList();
    ListView list = new ListView();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Simple Search");
        TextField txt = new TextField();
        txt.setPromptText("Search");
        txt.textProperty().addListener((observable, oldVal, newVal) -> search(oldVal, newVal));

        list.setMaxHeight(180);
        for (int i = 0; i < 100; i++) {
            entries.add("Item " + i);
        }
        entries.add("A");
        entries.add("B");
        entries.add("C");
        entries.add("D");
        list.setItems(entries);

        VBox root = new VBox();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setSpacing(2);
        root.getChildren().addAll(txt, list);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public void search(String oldVal, String newVal) {
        if (oldVal != null && (newVal.length() < oldVal.length())) {
            list.setItems(entries);
        }
        String value = newVal.toUpperCase();
        ObservableList<String> subentries = FXCollections.observableArrayList();
        for (Object entry : list.getItems()) {
            boolean match = true;
            String entryText = (String) entry;
            if (!entryText.toUpperCase().contains(value)) {
                match = false;
//                break;
            }
            if (match) {
                subentries.add(entryText);
            }
        }
        list.setItems(subentries);
    }
}