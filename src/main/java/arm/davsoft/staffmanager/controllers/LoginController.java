package arm.davsoft.staffmanager.controllers;

import arm.davsoft.staffmanager.Main;
import arm.davsoft.staffmanager.service.LoginService;
import arm.davsoft.staffmanager.stages.MainStage;
import arm.davsoft.staffmanager.utils.Dialogs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by david on 7/23/16.
 */
public class LoginController extends AppSpecController {

    @FXML
    private Button btn_login;
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.LOGGER.info("Login controller instantiation started.");
        prepareForm();
        Main.LOGGER.info("Login controller instantiated successfully.");
    }

    public void prepareForm() {

    }

    @FXML
    private void login(ActionEvent event) throws Exception {
        if (!"".equals(textField.getText().trim()) && !"".equals(passField.getText().trim())) {
            try {
//                PreparedStatement pst = ConnectionProvider.getInstance().getNewConnection().prepareStatement("SELECT * FROM users WHERE username = ? AND password = ? AND active = 1");
//                pst.setString(1, textField.getText().trim());
//                pst.setString(2, passField.getText().trim());
//                ResultSet resultSet = pst.executeQuery();
                if (LoginService.checkLoginData(textField.getText().trim(), passField.getText().trim())) {
                    new MainStage().showAndFocus();
                    currentStage.hide();
                } else {
//                    Dialogs.showErrorPopup("Access denied!", "Invalid username and/or password!");
                    Dialogs.showNotification(Dialogs.NotificationType.ERROR, "Access denied!", "Invalid username and/or password!");
                }
            } catch (Exception ex) {
                Main.LOGGER.error("Error during login. ", ex);
                throw ex;
            }
        } else {
            Dialogs.showNotification(Dialogs.NotificationType.WARNING, "Attention!", "Both username and password fields are required!");
        }
    }
}
