package arm.davsoft.staffmanager.utils.dialogs;

import arm.davsoft.staffmanager.utils.FXMLFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * <b>Author:</b> David Shahbazyan <br/>
 * <b>Date:</b> 10/21/15 <br/>
 * <b>Time:</b> 5:42 PM <br/>
 */
public abstract class CustomDialog {
    protected final Window ownerWindow;
    protected final Stage stage = new Stage();

    protected CustomDialog(Window ownerWindow) {
        this.ownerWindow = ownerWindow;
    }

    public void show() {
        stage.show();
    }
    public void showAndWait() {
        stage.showAndWait();
    }
    public void requestFocus() {
        stage.requestFocus();
    }
    public void close() {
        stage.close();
    }

    public void show(boolean requestFocus) {
        show();
        if (requestFocus) {
            requestFocus();
        }
    }

    protected void initSceneFromFxml(String fxmlLayoutFile) throws IOException {
        Parent root = FXMLFactory.getFXMLParent(fxmlLayoutFile);
        setScene(new Scene(root));
    }
    protected void initModality(Modality modality) {
        stage.initModality(modality);
    }
    protected void initOwner(Window ownerWindow) {
        stage.initOwner(ownerWindow);
    }
    protected void setTitle(String title) {
        stage.setTitle(title);
    }
    protected void setScene(Scene scene) {
        stage.setScene(scene);
    }
}
