package arm.davsoft.staffmanager.stages;

import arm.davsoft.staffmanager.Main;
import arm.davsoft.staffmanager.components.ApplicationTitleBar;
import arm.davsoft.staffmanager.controllers.AppSpecController;
import arm.davsoft.staffmanager.controllers.MainScreenController;
import arm.davsoft.staffmanager.utils.ResourceManager;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

/**
 * Created by david on 8/1/16.
 */
public class MainStage extends CustomStage {

    public MainStage() throws Exception {
        super();
        initStyle(StageStyle.UNDECORATED);
        initStage(ResourceManager.getParam("APPLICATION.NAME"), ResourceManager.getAppLogo());
        Parent parent = fxmlLoader.load(getClass().getResourceAsStream("/screens/mainScreen.fxml"));
        ((VBox) getScene().getRoot()).getChildren().addAll(new ApplicationTitleBar(this), parent);
        VBox.setVgrow(parent, Priority.ALWAYS);
        fixMinDimensionsTo(800, 640);
        ((AppSpecController) fxmlLoader.getController()).setCurrentStage(this);
    }
}
