package arm.davsoft.staffmanager.controllers;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * Created by david on 7/23/16.
 */
public abstract class AppSpecController<S extends Stage> implements Initializable {

    protected S currentStage;

    public void setCurrentStage(S currentStage) {
        this.currentStage = currentStage;
    }

    public void prepareForm() {
        initIcons();
    }

    protected void initIcons() {

    }
}
