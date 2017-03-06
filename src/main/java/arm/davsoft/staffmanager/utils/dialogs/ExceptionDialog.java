package arm.davsoft.staffmanager.utils.dialogs;

import arm.davsoft.staffmanager.utils.ResourceManager;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <b>Author:</b> David Shahbazyan <br/>
 * <b>Date:</b> 10/21/15 <br/>
 * <b>Time:</b> 3:00 PM <br/>
 */
public class ExceptionDialog {
    private final Alert alert = new Alert(Alert.AlertType.ERROR);
    private String header;
    private String content;
    private Throwable throwable;
    private Window ownerWindow;

    private ExceptionDialog(String header, String content, Throwable throwable, Window ownerWindow) {
        this.header = header;
        this.content = content;
        this.throwable = throwable;
        this.ownerWindow = ownerWindow;
    }

    public static ExceptionDialog create(String header, String content, Throwable throwable) {
        ExceptionDialog dialog = new ExceptionDialog(header, content, throwable, null);
        return dialog.prepare();
    }

    public static ExceptionDialog create(String header, String content, Throwable throwable, Window ownerWindow) {
        ExceptionDialog dialog = new ExceptionDialog(header, content, throwable, ownerWindow);
        return dialog.prepare();
    }

    private ExceptionDialog prepare() {
        alert.setTitle(ResourceManager.getMessage("title.dialog.error"));
        alert.setHeaderText(header);
        alert.setContentText(content);
        if (ownerWindow != null) {
            alert.initOwner(ownerWindow);
        }

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label(ResourceManager.getMessage("label.errorStackTraceWas"));

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.getDialogPane().expandedProperty().addListener((l) -> {
            Platform.runLater(() -> {
                alert.getDialogPane().requestLayout();
                Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
                stage.sizeToScene();
            });
        });

        return this;
    }

    public void showAndWait() {
        alert.showAndWait();
    }
}
