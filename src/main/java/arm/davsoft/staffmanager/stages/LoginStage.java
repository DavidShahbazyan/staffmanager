package arm.davsoft.staffmanager.stages;

import arm.davsoft.staffmanager.components.ApplicationTitleBar;
import arm.davsoft.staffmanager.controllers.AppSpecController;
import arm.davsoft.staffmanager.utils.ResourceManager;
import arm.davsoft.staffmanager.utils.Utils;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.util.Arrays;

/**
 * Created by david on 8/1/16.
 */
public class LoginStage extends CustomStage {

    public LoginStage() throws Exception {
        super();
        initStyle(StageStyle.UNDECORATED);
        initStage(Utils.concatObjects(Arrays.asList(ResourceManager.getParam("APPLICATION.NAME"), ResourceManager.getMessage("label.sign.in")), " - "), ResourceManager.getAppLogo());
        Parent parent = fxmlLoader.load(getClass().getResourceAsStream("/screens/loginScreen.fxml"));
        ((VBox) getScene().getRoot()).getChildren().addAll(new ApplicationTitleBar(this, true, false, true), parent);
        VBox.setVgrow(parent, Priority.ALWAYS);
//        fixAllDimensionsTo(264, 351);
        ((AppSpecController) fxmlLoader.getController()).setCurrentStage(this);
    }
}
